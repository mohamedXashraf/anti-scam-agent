package tech.mohamedashraf.domain.error

class ProductNotFoundError(val error: String) : Exception(error)