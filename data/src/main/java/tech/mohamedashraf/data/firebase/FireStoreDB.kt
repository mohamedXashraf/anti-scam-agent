package tech.mohamedashraf.data.firebase

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import tech.mohamedashraf.data.model.UserEntity
import tech.mohamedashraf.domain.rules.User

class FireStoreDB {

    // region ------------------- User -------------------

    suspend fun addUser(user: User): Boolean {
        return try {
            val userEntity = UserEntity.fromUser(user)
            Firebase.firestore.collection("users").document(userEntity.id).set(userEntity.toMap()).await()
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
            if (document.exists() && data != null) UserEntity.fromMap(data).toUser() else null
        } catch (error: Exception) {
            error.printStackTrace()
            null
        }
    }

    fun subscribeToUser(uid: String, listener: (User?) -> Unit) {
        try {
            Firebase.firestore.collection("users").where(Filter.equalTo("uid", uid)).addSnapshotListener { snapshot, error ->
                if (error != null) {
                    error.printStackTrace()
                    listener(null)
                } else if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val data = document.data
                        if (document.exists() && data != null) {
                            listener(UserEntity.fromMap(data).toUser())
                        }
                    }
                }
            }
        } catch (error: Exception) {
            error.printStackTrace()
            listener(null)
        }
    }

    suspend fun updateTokensBalance(uid: String, difference: Int): Boolean {
        return try {
            Firebase.firestore.collection("users").document(uid)
                .update("tokens", FieldValue.increment(difference.toLong()))
                .await()
            true
        } catch (error: Exception) {
            error.printStackTrace()
            false
        }
    }

    suspend fun deleteUser(uid: String) {
        Firebase.firestore.collection("users").document(uid).delete().await()
    }

    // endregion
}
