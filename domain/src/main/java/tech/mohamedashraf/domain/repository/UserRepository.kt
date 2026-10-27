package tech.mohamedashraf.domain.repository

interface UserRepository {

    suspend fun checkLoggedInUser(): String
}