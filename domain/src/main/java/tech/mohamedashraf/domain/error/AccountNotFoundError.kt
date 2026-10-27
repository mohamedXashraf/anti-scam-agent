package tech.mohamedashraf.domain.error

class AccountNotFoundError(val error: String) : Exception(error)