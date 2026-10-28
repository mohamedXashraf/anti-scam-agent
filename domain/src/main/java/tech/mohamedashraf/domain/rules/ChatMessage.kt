package tech.mohamedashraf.domain.rules

data class ChatMessage(
    val text: String,
    val sender: ChatParticipant,
    val sentAt: Long
) {
    companion object {
        fun fromMap(data: Map<String, Any>) = ChatMessage(
            text = data["text"] as? String ?: "",
            sender = (data["sender"] as? String)?.let { name -> ChatParticipant.entries.firstOrNull { it.name == name } } ?: ChatParticipant.ME,
            sentAt = (data["sentAt"] as? Number)?.toLong() ?: 0L
        )
    }

    fun toMap() = mapOf("text" to text, "sender" to sender.name, "sentAt" to sentAt)
}
