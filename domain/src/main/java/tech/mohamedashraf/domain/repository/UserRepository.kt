package tech.mohamedashraf.domain.repository

import tech.mohamedashraf.domain.rules.User

interface UserRepository {
    suspend fun checkLoggedInUser(): String
    suspend fun login(email: String, password: String): User?
    suspend fun register(name: String, email: String, password: String): User?
    suspend fun getUser(uid: String): User?
}