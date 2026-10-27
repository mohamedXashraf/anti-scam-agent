package tech.mohamedashraf.domain.error

class MissingInfoError(val error: String) : Exception(error)