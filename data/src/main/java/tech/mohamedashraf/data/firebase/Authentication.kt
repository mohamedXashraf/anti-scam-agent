package tech.mohamedashraf.data.firebase

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import tech.mohamedashraf.domain.error.AuthenticationFailedError

class Authentication {

    suspend fun getLoggedInUserEmail(): String = Firebase.auth.currentUser?.email ?: ""

    suspend fun checkLoggedInUser(): String {
        return try {
            Firebase.auth.currentUser?.reload()?.await()
            Firebase.auth.currentUser?.uid ?: ""
        } catch (error: Exception) {
            error.printStackTrace()
            ""
        }
    }

    suspend fun checkLoggedInUserVerification(): Boolean {
        return try {
            Firebase.auth.currentUser?.reload()?.await()
            Firebase.auth.currentUser != null && Firebase.auth.currentUser!!.isEmailVerified
        } catch (error: Exception) {
            error.printStackTrace()
            false
        }
    }

    fun listenUserAuthStateChanges(listener: (String) -> Unit) {
        Firebase.auth.addAuthStateListener { listener(it.currentUser?.uid ?: "") }
    }

    suspend fun login(email: String, password: String): String {
        try {
            val result = Firebase.auth.signInWithEmailAndPassword(email, password).await()
            return result.user?.uid ?: ""
        } catch (error: FirebaseAuthException) {
            error.printStackTrace()
            val message = when (error.errorCode) {
                "ERROR_USER_NOT_FOUND", "user-not-found", "ERROR_WRONG_PASSWORD", "wrong-password", "ERROR_INVALID_CREDENTIAL", "invalid-credential" -> "Invalid Email or Password! ... Please try again."
                else -> error.message ?: "Login Failed! ... Please try again."
            }
            throw AuthenticationFailedError(message)
        } catch (error: Exception) {
            error.printStackTrace()
            throw AuthenticationFailedError(error.message ?: "Login Failed! ... Please try again.")
        }
    }

    suspend fun signup(email: String, password: String): String {
        try {
            val result = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: ""
            if (userId.isNotEmpty()) {
                sendVerificationEmail()
                return userId
            } else {
                throw AuthenticationFailedError("Something went wrong! ... Please try again later.")
            }
        } catch (error: AuthenticationFailedError) {
            throw error
        } catch (error: FirebaseAuthUserCollisionException) {
            error.printStackTrace()
            throw AuthenticationFailedError("Account already exists!")
        } catch (error: FirebaseAuthException) {
            error.printStackTrace()
            val message = when (error.errorCode) {
                "ERROR_WEAK_PASSWORD", "weak-password" -> "Weak Password! ... Please try stronger password."
                "ERROR_EMAIL_ALREADY_IN_USE", "email-already-in-use" -> "Account already exists!"
                else -> error.message ?: "Something went wrong! ... Please try again later."
            }
            throw AuthenticationFailedError(message)
        } catch (error: Exception) {
            error.printStackTrace()
            throw AuthenticationFailedError(error.message ?: "Something went wrong! ... Please try again later.")
        }
    }

    suspend fun sendVerificationEmail() {
        Firebase.auth.currentUser?.sendEmailVerification()?.await()
    }

    suspend fun validateUserCredentials(email: String, password: String): Boolean {
        return try {
            Firebase.auth.currentUser?.reauthenticate(EmailAuthProvider.getCredential(email, password))?.await()
            true
        } catch (error: Exception) {
            error.printStackTrace()
            false
        }
    }

    suspend fun updatePassword(password: String): Boolean {
        return try {
            Firebase.auth.currentUser?.updatePassword(password)?.await()
            true
        } catch (error: Exception) {
            error.printStackTrace()
            false
        }
    }

    suspend fun deleteAccount() {
        Firebase.auth.currentUser?.delete()?.await()
    }

    fun logout() {
        Firebase.auth.signOut()
    }
}
