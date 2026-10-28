package tech.mohamedashraf.domain.repository

import tech.mohamedashraf.domain.rules.Product

interface MarketplaceRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProductDetails(productId: String): Product?
}