package tech.mohamedashraf.domain.error

class PasswordConfirmationMismatchError(val error: String) : Exception(error)