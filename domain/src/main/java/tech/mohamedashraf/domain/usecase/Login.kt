package tech.mohamedashraf.domain.usecase

import tech.mohamedashraf.domain.repository.UserRepository

class Login(private val userRepository: UserRepository) {

    suspend operator fun invoke() {}
}