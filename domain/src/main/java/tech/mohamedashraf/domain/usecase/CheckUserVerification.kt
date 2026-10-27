package tech.mohamedashraf.domain.usecase

import tech.mohamedashraf.domain.repository.UserRepository

class CheckUserVerification(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Boolean = userRepository.checkLoggedInUserVerification()
}