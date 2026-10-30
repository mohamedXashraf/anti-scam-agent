package tech.mohamedashraf.data.model

import com.google.gson.annotations.SerializedName

data class InputMessage(
    @SerializedName("content") val content: List<ContentPart>,
    @SerializedName("role") val role: String = "user",
    @SerializedName("type") val type: String = "message",
)