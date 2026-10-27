package tech.mohamedashraf.domain.error

class AuthenticationFailedError(val error: String) : Exception(error)
