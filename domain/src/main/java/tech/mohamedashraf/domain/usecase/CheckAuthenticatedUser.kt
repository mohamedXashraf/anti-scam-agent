package tech.mohamedashraf.domain.usecase

import tech.mohamedashraf.domain.repository.UserRepository
import tech.mohamedashraf.domain.rules.User

class CheckAuthenticatedUser(private val userRepository: UserRepository) {

    suspend operator fun invoke(): User? {
        val uid = userRepository.checkLoggedInUser()
        return if (uid.isNotBlank()) userRepository.getUser(uid) else null
    }
}