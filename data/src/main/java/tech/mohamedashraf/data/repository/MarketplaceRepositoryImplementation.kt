package tech.mohamedashraf.data.repository

import tech.mohamedashraf.data.firebase.FireStoreDB
import tech.mohamedashraf.data.firebase.FirebaseStorage
import tech.mohamedashraf.data.muse.Muse
import tech.mohamedashraf.domain.repository.MarketplaceRepository
import tech.mohamedashraf.domain.rules.DealVerification
import tech.mohamedashraf.domain.rules.Product
import java.io.File

class MarketplaceRepositoryImplementation(private val fireStoreDB: FireStoreDB, private val firebaseStorage: FirebaseStorage, private val muse: Muse) : MarketplaceRepository {

    private val products = mutableListOf<Product>()

    override suspend fun getProducts(): List<Product> {
        products.clear()
        products.addAll(fireStoreDB.getProducts() ?: listOf())
        return products
    }

    override suspend fun getProductDetails(productId: String): Product? {
        if (products.isEmpty()) getProducts()
        return products.find { it.id == productId }
    }

    override suspend fun verifyDeal(product: Product, productLiveVideo: File?): DealVerification {
        return muse.run(product, firebaseStorage.downloadFile(product.imageUrl), productLiveVideo)
    }
}