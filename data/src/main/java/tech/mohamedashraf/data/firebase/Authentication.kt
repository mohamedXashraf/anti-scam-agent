package tech.mohamedashraf.data.firebase

import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import tech.mohamedashraf.domain.error.*

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
            when (error.errorCode) {
                "ERROR_USER_NOT_FOUND", "user-not-found" -> throw AccountNotFoundError("Account Not Found! ... Please check again.")
                "ERROR_WRONG_PASSWORD", "wrong-password", "ERROR_INVALID_CREDENTIAL", "invalid-credential" -> throw InvalidCredentialsError("Invalid Email or Password! ... Please try again.")
                else -> throw AuthenticationFailedError(error.message ?: "Login Failed! ... Please try again.")
            }
        } catch (error: FirebaseAuthInvalidCredentialsException) {
            error.printStackTrace()
            throw InvalidCredentialsError("Invalid Email or Password! ... Please try again.")
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
        } catch (error: FirebaseAuthUserCollisionException) {
            error.printStackTrace()
            throw AccountCollisionError("Account already exists!")
        } catch (error: FirebaseAuthInvalidCredentialsException) {
            error.printStackTrace()
            throw InvalidCredentialsError("Invalid Email or Password! ... Please try again.")
        } catch (error: FirebaseAuthException) {
            error.printStackTrace()
            when (error.errorCode) {
                "ERROR_WEAK_PASSWORD", "weak-password" -> throw InvalidCredentialsError("Weak Password! ... Please try stronger password.")
                "ERROR_EMAIL_ALREADY_IN_USE", "email-already-in-use" -> throw AccountCollisionError("Account already exists!")
                else -> throw AuthenticationFailedError(error.message ?: "Something went wrong! ... Please try again later.")
            }
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
