package tech.mohamedashraf.domain.repository

import tech.mohamedashraf.domain.rules.User

interface UserRepository {
    suspend fun checkLoggedInUser(): String
    suspend fun login(email: String, password: String): User?
    suspend fun register(name: String, email: String, password: String): User?
    suspend fun checkLoggedInUserVerification(): Boolean
    suspend fun sendVerificationEmail()
    suspend fun getUser(uid: String): User?
    suspend fun updateUser(user: User)
    suspend fun logout()
}