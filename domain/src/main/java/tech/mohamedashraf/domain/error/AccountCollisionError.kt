package tech.mohamedashraf.domain.error

class AccountCollisionError(val error: String) : Exception(error)