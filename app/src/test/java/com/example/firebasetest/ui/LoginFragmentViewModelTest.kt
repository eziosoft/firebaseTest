package com.example.firebasetest.ui

import androidx.lifecycle.SavedStateHandle
import com.example.firebasetest.firebase.FireBaseTest
import com.example.firebasetest.utils.Validator
import com.netguru.android.hooda.common.TestCoroutineRule
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginFragmentViewModelTest {

    private lateinit var viewModel: LoginFragmentViewModel

    @get:Rule
    val coroutineRule = TestCoroutineRule()


    @Before
    fun init() = coroutineRule.runBlockingTest {
        viewModel = LoginFragmentViewModel(Validator(), SavedStateHandle() ,FireBaseTest(mock(), mock()))
    }

    @Test
    fun `check email not valid`() {
        val email = "sdadafaf"
        var password = "dasadfaf123#ffa"

        viewModel.loginUser(email, password) { state, exception ->
            assertEquals(state, false)
            assert(exception is LoginFragmentViewModel.CustomExceptions.EmailNotValidException)
        }
    }

    @Test
    fun `check email is valid`() {
        val email = "sdadafaf@fada.com"
        var password = "dasadfaf123#ffa"

        viewModel.loginUser(email, password) { state, exception ->
            assertFalse(exception is LoginFragmentViewModel.CustomExceptions.EmailNotValidException)
        }
    }


    @Test
    fun `check password is empty`() {
        val email = "sdadafaf@fada.com"
        var password = ""

        viewModel.loginUser(email, password) { state, exception ->
            assertEquals(state, false)
            assert(exception is LoginFragmentViewModel.CustomExceptions.PasswordNotValidException)
        }
    }

    @Test
    fun `check password is valid`() {
        val email = "sdadafaf@fada.com"
        var password = "dasadfaf123#ffa"

        viewModel.loginUser(email, password) { state, exception ->
            assertFalse(exception == null)
        }
    }

}