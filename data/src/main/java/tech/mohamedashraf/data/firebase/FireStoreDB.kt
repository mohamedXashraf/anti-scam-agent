package tech.mohamedashraf.data.firebase

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import tech.mohamedashraf.data.model.UserEntity

class FireStoreDB {

    // region ------------------- User -------------------

    suspend fun addUser(user: UserEntity): Boolean {
        return try {
            Firebase.firestore.collection("users").document(user.id).set(user.toMap()).await()
            true
        } catch (error: Exception) {
            error.printStackTrace()
            false
        }
    }

    suspend fun getUser(uid: String): UserEntity? {
        return try {
            val document = Firebase.firestore.collection("users").document(uid).get().await()
            val data = document.data
            if (document.exists() && data != null) UserEntity.fromMap(data) else null
        } catch (error: Exception) {
            error.printStackTrace()
            null
        }
    }

    fun subscribeToUser(uid: String, listener: (UserEntity?) -> Unit) {
        try {
            Firebase.firestore.collection("users").where(Filter.equalTo("uid", uid)).addSnapshotListener { snapshot, error ->
                if (error != null) {
                    error.printStackTrace()
                    listener(null)
                } else if (snapshot != null) {
                    for (document in snapshot.documents) {
                        val data = document.data
                        if (document.exists() && data != null) {
                            listener(UserEntity.fromMap(data))
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
