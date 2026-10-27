package tech.mohamedashraf.domain.error

class InvalidCredentialsError(val error: String) : Exception(error)