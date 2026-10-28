package tech.mohamedashraf.data.repository

import tech.mohamedashraf.data.firebase.FireStoreDB
import tech.mohamedashraf.domain.repository.MarketplaceRepository
import tech.mohamedashraf.domain.rules.Product

class MarketplaceRepositoryImplementation(private val fireStoreDB: FireStoreDB) : MarketplaceRepository {

    private val products = mutableListOf<Product>()

    override suspend fun getProducts(): List<Product> {
        products.clear()
        products.addAll(fireStoreDB.getProducts() ?: listOf())
        return products
    }

    override suspend fun getProductDetails(productId: String): Product? {
        return products.find { it.id == productId }
    }
}