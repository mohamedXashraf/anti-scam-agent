package tech.mohamedashraf.data.model

import com.google.gson.annotations.SerializedName
import tech.mohamedashraf.domain.rules.DealVerification
import tech.mohamedashraf.domain.rules.DealVerificationNote

data class AgentDealVerificationResult(
    @SerializedName("notes") val notes: List<AgentDealVerificationNote> = listOf(),
    @SerializedName("recommendation") val recommendation: String = ""
) {
    fun toDealVerification() = DealVerification(
        notes = notes.map { DealVerificationNote(it.note, it.accepted) },
        recommendation = recommendation
    )
}

data class AgentDealVerificationNote(
    @SerializedName("note") val note: String = "",
    @SerializedName("accepted") val accepted: Boolean = false
)