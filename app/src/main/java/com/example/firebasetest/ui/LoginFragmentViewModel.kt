package com.example.firebasetest.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasetest.AlertType
import com.example.firebasetest.TAG
import com.example.firebasetest.firebase.FireBaseTest
import com.example.firebasetest.showAlert
import com.example.firebasetest.showToast
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    state: SavedStateHandle,
    private val fireBaseTest: FireBaseTest
) : ViewModel() {

//    private val _authFlow = MutableSharedFlow<CurrentUser>()
//    val authFlow: Flow<CurrentUser> = _authFlow

    val currentUser = MutableLiveData<CurrentUser>()

    data class CurrentUser(val authenticated: Boolean, val firebaseUser: FirebaseUser?)

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
        fireBaseTest.firebaseAuthentication.logIn(
            email,
            password
        ) { result ->
            state(result.isSuccessful, result.exception)

        }
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
        fireBaseTest.firebaseAuthentication.registerUser(email, password) { result ->
            status(result.isSuccessful, result.exception)
        }
    }

    fun logoutUser() {
        fireBaseTest.firebaseAuthentication.logout()
    }
}