package com.example.firebasetest.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.firebasetest.firebase.FireBaseTest
import com.example.firebasetest.utils.Validator
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val validator: Validator,
    state: SavedStateHandle,
    private val fireBaseTest: FireBaseTest
) : ViewModel() {

    data class CurrentUser(val authenticated: Boolean, val firebaseUser: FirebaseUser?)

    val currentUser = MutableLiveData<CurrentUser>()

    init {
        fireBaseTest.firebaseAuthentication.addAuthListener { user ->
            currentUser.postValue(CurrentUser(user != null, user))
        }
    }


    fun loginUser(
        email: String,
        password: String,
        state: (success: Boolean, exception: Exception?) -> Unit
    ) {

        if (validator.isEmailValid(email)) {
            if (validator.isPasswordValid(password)) {
                fireBaseTest.firebaseAuthentication.logIn(
                    email,
                    password
                ) { result ->
                    state(result.isSuccessful, result.exception)
                }
            } else {
                state(false, CustomExceptions.PasswordNotValidException("Password is not valid"))
            }
        } else state(false, CustomExceptions.EmailNotValidException("Email is not valid"))

    }

    sealed class CustomExceptions(message: String) : Exception(message) {
        class EmailNotValidException(message: String) : CustomExceptions(message)
        class PasswordNotValidException(message: String) : CustomExceptions(message)
    }

    fun deleteUser(status: (success: Boolean, exception: Exception?) -> Unit) {
        fireBaseTest.firebaseAuthentication.deleteUser { result ->
            status(result.isSuccessful, result.exception)
        }
    }


    fun registerUser(
        email: String,
        password: String,
        status: (success: Boolean, exception: Exception?) -> Unit
    ) {


        if (validator.isEmailValid(email)) {
            if (validator.isPasswordValid(password)) {
                fireBaseTest.firebaseAuthentication.registerUser(email, password) { result ->
                    status(result.isSuccessful, result.exception)
                }
            } else {
                status(false, java.lang.Exception("Password is not valid"))
            }
        } else status(false, java.lang.Exception("Email is not valid"))


    }

    fun logoutUser() {
        fireBaseTest.firebaseAuthentication.logout()
    }
}