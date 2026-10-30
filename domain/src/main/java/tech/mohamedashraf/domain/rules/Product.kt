package tech.mohamedashraf.domain.rules

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val sellerId: String,
    val chat: List<ChatMessage>
) {
    companion object {
        @Suppress("UNCHECKED_CAST")
        fun fromMap(data: Map<String, Any>) = Product(
            id = data["id"] as? String ?: "",
            name = data["name"] as? String ?: "",
            description = data["description"] as? String ?: "",
            price = (data["price"] as? Number)?.toDouble() ?: 0.0,
            imageUrl = data["imageUrl"] as? String ?: "",
            sellerId = data["sellerId"] as? String ?: "",
            chat = (data["chat"] as? List<*>)?.mapNotNull { (it as? Map<String, Any>)?.let(ChatMessage::fromMap) } ?: emptyList()
        )
    }

    fun toMap() = mapOf(
        "id" to id,
        "name" to name,
        "description" to description,
        "price" to price,
        "imageUrl" to imageUrl,
        "sellerId" to sellerId,
        "chat" to chat.map { it.toMap() }
    )

    fun toPromptMap() = mapOf(
        "name" to name,
        "description" to description,
        "price" to price,
        "chat" to chat.map { it.toMap() }
    )
}