package tech.mohamedashraf.data.muse

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import tech.mohamedashraf.data.model.AgentRequest
import tech.mohamedashraf.data.model.AgentResponse
import tech.mohamedashraf.data.model.FileObject

interface MuseAPIs {

    @Multipart
    @POST("files")
    suspend fun uploadFile(
        @Part purpose: MultipartBody.Part,
        @Part expiresAnchor: MultipartBody.Part,
        @Part expiresSeconds: MultipartBody.Part,
        @Part file: MultipartBody.Part,
    ): FileObject

    @DELETE("files/{id}")
    suspend fun deleteFile(@Path("id") fileId: String)

    @POST("responses")
    suspend fun createResponse(@Body request: AgentRequest): AgentResponse
}
