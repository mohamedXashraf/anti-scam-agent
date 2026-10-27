package tech.mohamedashraf.data.model

import tech.mohamedashraf.domain.rules.User

data class UserEntity(
    val id: String = "", val name: String = "", val email: String = ""
) {

    companion object {
        fun fromMap(data: Map<String, Any>) = UserEntity(
            id = data["uid"] as? String ?: "",
            name = data["name"] as? String ?: "",
            email = data["email"] as? String ?: ""
        )

        fun fromUser(user: User) = UserEntity(id = user.id, name = user.name, email = user.email)
    }

    fun toMap() = mapOf("uid" to id, "name" to name, "email" to email.lowercase())

    fun toUser() = User(id = id, name = name, email = email)
}
