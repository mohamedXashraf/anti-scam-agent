package tech.mohamedashraf.data.model

import tech.mohamedashraf.domain.rules.User

data class UserEntity(
    val id: String = "", val name: String = "", val email: String = "", val verified: Boolean = false
) {

    companion object {
        fun fromMap(data: Map<String, Any>) = UserEntity(
            id = data["uid"] as? String ?: "",
            name = data["name"] as? String ?: "",
            email = data["email"] as? String ?: "",
            verified = data["verified"] as? Boolean ?: false
        )

        fun fromUser(user: User) = UserEntity(id = user.id, name = user.name, email = user.email, verified = user.verified)
    }

    fun toMap() = mapOf("uid" to id, "name" to name, "email" to email.lowercase(), "verified" to verified)

    fun toUser() = User(id = id, name = name, email = email, verified = verified)
}
