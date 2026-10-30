package tech.mohamedashraf.data.model

import com.google.gson.annotations.SerializedName

data class AgentRequest(
    @SerializedName("input") val input: List<InputMessage>,
    @SerializedName("model") val model: String = "muse-spark-1.3",
    @SerializedName("instructions") val instructions: String? = null,
    @SerializedName("max_output_tokens") val maxOutputTokens: Int = 4096,
)
