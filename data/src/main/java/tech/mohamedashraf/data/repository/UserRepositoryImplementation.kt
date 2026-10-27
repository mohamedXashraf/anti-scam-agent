package tech.mohamedashraf.data.repository

import tech.mohamedashraf.data.firebase.Authentication
import tech.mohamedashraf.data.firebase.FireStoreDB
import tech.mohamedashraf.data.model.UserEntity
import tech.mohamedashraf.domain.repository.UserRepository
import tech.mohamedashraf.domain.rules.User

class UserRepositoryImplementation(private val authentication: Authentication, private val fireStoreDB: FireStoreDB) : UserRepository {

    override suspend fun checkLoggedInUser(): String = authentication.checkLoggedInUser()

    override suspend fun login(email: String, password: String): User? {
        val uid = authentication.login(email, password)
        return if (uid.isNotBlank()) {
            fireStoreDB.getUser(uid)?.toUser()
        } else null
    }

    override suspend fun register(name: String, email: String, password: String): User? {
        val uid = authentication.signup(email, password)
        return if (uid.isNotBlank()) {
            val user = User(uid, name, email, false)
            if (fireStoreDB.addUser(UserEntity.fromUser(user))) user else null
        } else null
    }

    override suspend fun getUser(uid: String): User? {
        return if (uid.isNotBlank()) {
            fireStoreDB.getUser(uid)?.toUser()
        } else null
    }
}