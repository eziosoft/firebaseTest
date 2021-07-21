package com.example.firebasetest.utils

import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Singleton


class Validator @Inject constructor(){

    private val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
    private val EMAIL_PATTERN = "^[^@]+@[a-zA-Z0-9._-]+\\.+[a-z._-]+\$"

    fun isEmailValid(email: String) = email.isNotEmpty() && Pattern.compile(EMAIL_PATTERN).matcher(email).matches()
    fun isPasswordValid(password: String) = password.isNotEmpty() && Pattern.compile(PASSWORD_PATTERN).matcher(password).matches()

    fun showHideInputLayoutError(layout: TextInputLayout, hasValidInput: Boolean, errorResource: Int) {
        layout.error = when (hasValidInput) {
            true -> ""
            false -> layout.context.getString(errorResource)
        }
    }

}