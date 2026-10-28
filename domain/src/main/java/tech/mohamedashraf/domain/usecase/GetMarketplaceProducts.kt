package tech.mohamedashraf.domain.usecase

import tech.mohamedashraf.domain.repository.MarketplaceRepository
import tech.mohamedashraf.domain.repository.UserRepository
import tech.mohamedashraf.domain.rules.Product

class GetMarketplaceProducts(
    private val userRepository: UserRepository,
    private val marketplaceRepository: MarketplaceRepository
) {
    suspend operator fun invoke(): List<Product> {
        return if (userRepository.checkLoggedInUser().isNotEmpty()) {
            marketplaceRepository.getProducts()
        } else listOf()
    }
}