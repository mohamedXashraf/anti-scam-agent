package tech.mohamedashraf.domain.usecase

import tech.mohamedashraf.domain.repository.UserRepository

class SendVerificationEmail(private val userRepository: UserRepository) {

    suspend operator fun invoke() {
        userRepository.sendVerificationEmail()
    }
}