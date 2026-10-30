package tech.mohamedashraf.data.model

import com.google.gson.annotations.SerializedName

data class AgentResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("output") val output: List<OutputItem>?,
    @SerializedName("usage") val usage: Usage?,
) {
    val outputText: String
        get() = output.orEmpty()
            .filter { it.type == "message" }
            .flatMap { it.content.orEmpty() }
            .filter { it.type == "output_text" }
            .joinToString("") { it.text.orEmpty() }
}

data class OutputItem(
    @SerializedName("id") val id: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("content") val content: List<OutputContent>?,
)

data class OutputContent(
    @SerializedName("type") val type: String?,
    @SerializedName("text") val text: String?,
)

data class Usage(
    @SerializedName("input_tokens") val inputTokens: Int?,
    @SerializedName("output_tokens") val outputTokens: Int?,
    @SerializedName("output_tokens_details") val outputDetails: OutputTokenDetails?,
)

data class OutputTokenDetails(
    @SerializedName("reasoning_tokens") val reasoningTokens: Int?,
)