package tech.mohamedashraf.data.muse

import tech.mohamedashraf.domain.rules.DealVerification
import tech.mohamedashraf.domain.rules.Product
import java.io.File

class MuseAPI {

    private val systemPrompt = """
        You check and verify a product before going on with the deal.
        Use only the provided chat thread JSON and product listing photo and an optional provided short video for the product on meetup with the seller.
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
    """.trimIndent()

    private fun prompt(product: Product, liveVideoAttached: Boolean) = """
        Verify this deal before finishing it .. 
        the photo is attached${if (liveVideoAttached) ", a short live video for product attached" else ""} and here is the product details and chat thread:
        ${product.toPromptMap()}
    """.trimIndent()

    fun run(product: Product, productListingPhoto: File, productLiveVideo: File?): DealVerification {
        prompt(product, productLiveVideo != null)
        return DealVerification()
    }
}