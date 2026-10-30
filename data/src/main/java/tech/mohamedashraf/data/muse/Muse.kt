package tech.mohamedashraf.data.muse

import android.util.Base64
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import tech.mohamedashraf.data.model.AgentDealVerificationResult
import tech.mohamedashraf.data.model.AgentRequest
import tech.mohamedashraf.data.model.ContentPart
import tech.mohamedashraf.data.model.InputMessage
import tech.mohamedashraf.domain.rules.DealVerification
import tech.mohamedashraf.domain.rules.Product
import java.io.File

class Muse(retrofit: Retrofit) {

    private val api = retrofit.create(MuseAPIs::class.java)

    private fun prompt(product: Product, liveVideoAttached: Boolean) = """
        You check and verify a product before going on with the deal.
        Use only the provided product details JSON and product listing photo and an optional provided short video for the product on meetup with the seller.
        Reply in JSON form only following the following scheme:
        {
            "notes": {
            "type": "List",
            "parameters": {
              "note": {
                "type": "String",
                "description": "Observation or verification check detail"
              },
              "accepted": {
                "type": "Boolean",
                "description": "Whether this verification check passed/is accepted"
              }
            }
          },
          "recommendation": {
            "type": "String",
            "description": "final recommendation prase whether Buy or Bargain or Scam and should be avoided"
          }
        }

        Now Verify this deal before finishing it ..  
        the photo is attached${if (liveVideoAttached) ", a short live video for product attached" else ""} and here is the product details and chat thread:
        ${product.toPromptMap()}
    """.trimIndent()

    suspend fun run(product: Product, productListingPhoto: Pair<ByteArray, String>, productLiveVideo: File?): DealVerification {
        val prompt = prompt(product, productLiveVideo != null)
        val productLiveVideoId = if (productLiveVideo != null) uploadProductLiveVideo(productLiveVideo) else ""
        val productListingPhotoData ="data:${productListingPhoto.second};base64,${Base64.encodeToString(productListingPhoto.first, Base64.NO_WRAP)}"
        val response = callAgent(prompt, productListingPhotoData, productLiveVideoId)
        return Gson().fromJson(response, AgentDealVerificationResult::class.java).toDealVerification()
    }

    private suspend fun uploadProductLiveVideo(productLiveVideo: File): String {
        return api.uploadFile(
            purpose = MultipartBody.Part.createFormData("purpose", "user_data"),
            expiresAnchor = MultipartBody.Part.createFormData("expires_after[anchor]", "created_at"),
            expiresSeconds = MultipartBody.Part.createFormData("expires_after[seconds]", 3600.toString()),
            file = MultipartBody.Part.createFormData("file", productLiveVideo.name, productLiveVideo.asRequestBody("video/mp4".toMediaType())),
        ).id
    }

    private suspend fun callAgent(prompt: String, productListingPhoto: String, productLiveVideoId: String): String {
        val content = mutableListOf(
            ContentPart.text(prompt),
            ContentPart.image(productListingPhoto),
        )
        if (productLiveVideoId.isNotBlank()) {
            content.add(ContentPart.videoFile(productLiveVideoId))
        }
        val request = AgentRequest(instructions = prompt, input = listOf(InputMessage(content = content)))
        val result = api.createResponse(request)
        if (result.status == "incomplete") {
            throw IllegalStateException("Muse ran out of output tokens. Raise maxOutputTokens.")
        }
        if (productLiveVideoId.isNotBlank()) {
            api.deleteFile(productLiveVideoId)
        }
        return result.outputText
    }
}