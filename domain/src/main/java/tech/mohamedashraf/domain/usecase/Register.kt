package tech.mohamedashraf.domain.usecase

import tech.mohamedashraf.domain.error.InvalidEmailError
import tech.mohamedashraf.domain.error.MissingInfoError
import tech.mohamedashraf.domain.error.PasswordConfirmationMismatchError
import tech.mohamedashraf.domain.repository.UserRepository
import tech.mohamedashraf.domain.rules.User
import tech.mohamedashraf.domain.utils.isValidEmail

class Register(private val userRepository: UserRepository) {

    suspend operator fun invoke(name: String, email: String, password: String, passwordConfirmation: String): User? {
        return when {
            name.isBlank() -> throw MissingInfoError("Name is required")
            email.isBlank() -> throw MissingInfoError("Email is required")
            password.isBlank() -> throw MissingInfoError("Password is required")
            passwordConfirmation.isBlank() -> throw MissingInfoError("Password is required")
            password != passwordConfirmation -> throw PasswordConfirmationMismatchError("Password confirmation does not match")
            !email.isValidEmail() -> throw InvalidEmailError("Invalid Email")
            else -> userRepository.register(name, email, password)
        }
    }
}