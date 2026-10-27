package tech.mohamedashraf.domain.error

class InvalidEmailError(val error: String) : Exception(error)