package tech.mohamedashraf.domain.repository

import tech.mohamedashraf.domain.rules.DealVerification
import tech.mohamedashraf.domain.rules.Product
import java.io.File

interface MarketplaceRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProductDetails(productId: String): Product?
    suspend fun verifyDeal(product: Product, productLiveVideo: File?): DealVerification
}