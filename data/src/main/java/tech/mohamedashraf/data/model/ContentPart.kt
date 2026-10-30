package tech.mohamedashraf.data.model

import com.google.gson.annotations.SerializedName

data class ContentPart(
    @SerializedName("type") val type: String,
    @SerializedName("text") val text: String? = null,
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("video_url") val videoUrl: String? = null,
    @SerializedName("file_id") val fileId: String? = null,
) {
    companion object {
        fun text(text: String) = ContentPart(type = "input_text", text = text)

        fun image(urlOrDataUrl: String) = ContentPart(type = "input_image", imageUrl = urlOrDataUrl)

        fun videoFile(fileId: String) = ContentPart(type = "input_video", fileId = fileId)

        fun videoUrl(urlOrDataUrl: String) = ContentPart(type = "input_video", videoUrl = urlOrDataUrl)
    }
}