package tech.mohamedashraf.data.firebase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import tech.mohamedashraf.domain.rules.Product
import tech.mohamedashraf.domain.rules.User

class FireStoreDB {

    // region ------------------- User -------------------

    suspend fun addUser(user: User): Boolean {
        return try {
            Firebase.firestore.collection("users").document(user.id).set(user.toMap()).await()
            true
        } catch (error: Exception) {
            error.printStackTrace()
            false
        }
    }

    suspend fun getUser(uid: String): User? {
        return try {
            val document = Firebase.firestore.collection("users").document(uid).get().await()
            val data = document.data
            if (document.exists() && data != null) User.fromMap(data) else null
        } catch (error: Exception) {
            error.printStackTrace()
            null
        }
    }

    suspend fun deleteUser(uid: String) {
        Firebase.firestore.collection("users").document(uid).delete().await()
    }

    // endregion

    // region ------------------- Marketplace -------------------

    suspend fun addProduct(product: Product): Boolean {
        return try {
            Firebase.firestore.collection("products").document(product.id).set(product.toMap()).await()
            true
        } catch (error: Exception) {
            error.printStackTrace()
            false
        }
    }

    suspend fun getProducts(): List<Product>? {
        return try {
            val products = mutableListOf<Product>()
            val snapshot = Firebase.firestore.collection("products").get().await()
            if (snapshot != null && !snapshot.isEmpty && snapshot.documents.isNotEmpty()) {
                for (document in snapshot.documents) {
                    val data = document.data
                    if (document.exists() && data != null) {
                        products.add(Product.fromMap(data))
                    }
                }
            }
            products
        } catch (error: Exception) {
            error.printStackTrace()
            null
        }
    }

    // endregion
}
