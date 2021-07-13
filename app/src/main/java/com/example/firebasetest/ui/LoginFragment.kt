package com.example.firebasetest.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firebasetest.*
import com.example.firebasetest.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var fireBaseTest: FireBaseTest

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)






        binding.goToDatabaseB.setOnClickListener() {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToDataBaseFragment())
        }


        binding.deleteUserB.setOnClickListener() {
            fireBaseTest.deleteUser { result ->
                if (result.isSuccessful) {
                    showToast("User deleted")
                } else {
                    showToast(result.exception?.message.toString())
                    Log.e(TAG, "delete user: ", result.exception)
                }
            }
        }

        binding.signInB.setOnClickListener() {
            val username = binding.loginET.text.toString()
            val pass = binding.passET.text.toString()
            fireBaseTest.registerUser(username, pass) { result ->
                if (result.isSuccessful) {
                    showToast("New User created")
                } else {
                    showToast(result.exception?.message.toString())
                    Log.e(TAG, "create user: ", result.exception)
                }
            }
        }


        fireBaseTest.addAuthListener { user ->
            if (user != null)
                updateScreen(fireBaseTest.currentUser())
            else
                binding.statusTV.text = "Not logged in"
        }


        binding.loginB.setOnClickListener() {
            val username = binding.loginET.text.toString()
            val pass = binding.passET.text.toString()
            Log.d(TAG, "login: $username, $pass ")

            fireBaseTest.logIn(
                username,
                pass
            ) { result ->

                if (result.isSuccessful) {
                    Log.v(TAG, "User logged in")
                    showToast("Login successful")

                } else {
                    Log.e(TAG, result.exception.toString())
                    showToast(result.exception?.message.toString())
                }
            }
        }

        binding.logOutB.setOnClickListener() {
            fireBaseTest.logout()
        }


//        if (fireBaseTest.currentUser() != null)
//            updateScreen(fireBaseTest.currentUser())
//        else
//            binding.statusTV.text = "Not logged in"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun updateScreen(user: FirebaseUser?) {
        user?.let {
            var s = ""
            s += "name:" + it.displayName + "\n"
            s += "email:" + it.email + "\n"
            s += "email verified:" + it.isEmailVerified.toString() + "\n"
            s += "phone:" + it.phoneNumber + "\n"
            s += "photoURL:" + it.photoUrl.toString() + "\n"
            s += "providerID:" + it.providerId + "\n"
            s += "UID:" + it.uid + "\n"
            binding.statusTV.text = s

//            Glide.with(this).load(it.photoUrl).into(findViewById(R.id.imageView))
        }
    }


}