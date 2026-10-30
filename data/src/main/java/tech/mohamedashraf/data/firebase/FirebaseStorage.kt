package tech.mohamedashraf.data.firebase

import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await

class FirebaseStorage {

    suspend fun downloadFile(downloadUrl: String): Pair<ByteArray, String> {
        val ref = Firebase.storage.getReferenceFromUrl(downloadUrl)
        val bytes = ref.getBytes(10L * 1024 * 1024).await()
        val mime = ref.metadata.await().contentType ?: "image/jpeg"
        return bytes to mime
    }
}