package tech.mohamedashraf.domain.usecase

import tech.mohamedashraf.domain.repository.UserRepository
import tech.mohamedashraf.domain.rules.User

class CheckAuthenticatedUser(private val userRepository: UserRepository) {

    suspend operator fun invoke(): User? {
        val uid = userRepository.checkLoggedInUser()
        return if (uid.isNotBlank()) {
            val user = userRepository.getUser(uid)
            if (user != null) user.verified = userRepository.checkLoggedInUserVerification()
            user
        } else null
    }
}