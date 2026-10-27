package tech.mohamedashraf.domain.usecase

import tech.mohamedashraf.domain.repository.UserRepository

class CheckAuthenticatedUser(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Boolean = userRepository.checkLoggedInUser().isNotBlank()
}