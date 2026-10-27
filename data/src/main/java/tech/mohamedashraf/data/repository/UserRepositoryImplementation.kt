package tech.mohamedashraf.data.repository

import tech.mohamedashraf.data.firebase.Authentication
import tech.mohamedashraf.data.firebase.FireStoreDB
import tech.mohamedashraf.domain.repository.UserRepository

class UserRepositoryImplementation(private val authentication: Authentication, fireStoreDB: FireStoreDB) : UserRepository {

    override suspend fun checkLoggedInUser(): String = authentication.checkLoggedInUser()
}