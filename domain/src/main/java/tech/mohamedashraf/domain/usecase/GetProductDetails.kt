package tech.mohamedashraf.domain.usecase

import tech.mohamedashraf.domain.repository.MarketplaceRepository

class GetProductDetails(private val marketplaceRepository: MarketplaceRepository) {

    suspend operator fun invoke(productId: String) = marketplaceRepository.getProductDetails(productId)
}