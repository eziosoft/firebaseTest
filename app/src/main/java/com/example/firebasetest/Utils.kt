package com.example.firebasetest

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast( message: String) {
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
}