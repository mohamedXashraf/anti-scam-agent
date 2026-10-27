package tech.mohamedashraf.domain.usecase

import tech.mohamedashraf.domain.repository.UserRepository

class Logout(private val userRepository: UserRepository) {

    suspend operator fun invoke() {
        userRepository.logout()
    }
}