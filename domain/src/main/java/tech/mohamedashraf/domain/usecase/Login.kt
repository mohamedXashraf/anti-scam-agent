package tech.mohamedashraf.domain.usecase

import tech.mohamedashraf.domain.repository.UserRepository
import kotlin.uuid.Uuid

class Login(private val userRepository: UserRepository) {

    suspend operator fun invoke() {

    }
}