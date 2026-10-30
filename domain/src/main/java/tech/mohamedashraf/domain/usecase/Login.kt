package tech.mohamedashraf.domain.usecase

import tech.mohamedashraf.domain.error.InvalidEmailError
import tech.mohamedashraf.domain.error.MissingInfoError
import tech.mohamedashraf.domain.repository.UserRepository
import tech.mohamedashraf.domain.rules.User
import tech.mohamedashraf.domain.utils.isValidEmail

class Login(private val userRepository: UserRepository) {

    suspend operator fun invoke(email: String, password: String): User? {
        return when {
            email.isBlank() -> throw MissingInfoError("Email is required")
            password.isBlank() -> throw MissingInfoError("Password is required")
            !email.isValidEmail() -> throw InvalidEmailError("Invalid email")
            else -> {
                val user = userRepository.login(email, password)
                if (user != null) user.verified = userRepository.checkLoggedInUserVerification()
                user
            }
        }
    }
}