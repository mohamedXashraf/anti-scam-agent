package tech.mohamedashraf.domain.rules

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    var verified: Boolean = false
) {
    companion object {
        fun fromMap(data: Map<String, Any>) = User(
            id = data["uid"] as? String ?: "",
            name = data["name"] as? String ?: "",
            email = data["email"] as? String ?: "",
            verified = data["verified"] as? Boolean ?: false
        )
    }

    fun toMap() = mapOf("uid" to id, "name" to name, "email" to email.lowercase(), "verified" to verified)
}
