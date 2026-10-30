package tech.mohamedashraf.domain.usecase

import tech.mohamedashraf.domain.error.ProductNotFoundError
import tech.mohamedashraf.domain.repository.MarketplaceRepository
import tech.mohamedashraf.domain.rules.DealVerification
import java.io.File

class StartAgenticDealVerification(private val marketplaceRepository: MarketplaceRepository) {

    suspend operator fun invoke(productId: String, video: File?): DealVerification {
        val product = marketplaceRepository.getProductDetails(productId)
        return if (product != null) {
            marketplaceRepository.verifyDeal(product, video)
        } else throw ProductNotFoundError("Invalid Product!")
    }
}