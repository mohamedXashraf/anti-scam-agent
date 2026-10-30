package tech.mohamedashraf.domain.usecase

import tech.mohamedashraf.domain.error.ProductNotFoundError
import tech.mohamedashraf.domain.repository.MarketplaceRepository
import tech.mohamedashraf.domain.rules.DealVerification
import tech.mohamedashraf.domain.rules.DealVerificationNote
import java.io.File

class StartAgenticDealVerification(private val marketplaceRepository: MarketplaceRepository) {

    suspend operator fun invoke(productId: String, video: File?): DealVerification {
        val product = marketplaceRepository.getProductDetails(productId)
        return if (product != null) {
            // marketplaceRepository.verifyDeal(product, video)
            DealVerification(
                notes = listOf(
                    DealVerificationNote("Product in video matches the listing description", true),
                    DealVerificationNote("Product in video matches the listing photo state", true),
                    DealVerificationNote("Seller refused to show serial number", false),
                ),
                recommendation = "You can bargain for it, it is a fair deal."
            )
        } else throw ProductNotFoundError("Invalid Product!")
    }
}