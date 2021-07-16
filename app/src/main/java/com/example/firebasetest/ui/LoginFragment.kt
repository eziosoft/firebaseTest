package com.example.firebasetest.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.firebasetest.*
import com.example.firebasetest.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewModel by viewModels<LoginFragmentViewModel>()

    private val binding get() = _binding!!
    private var _binding: FragmentLoginBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)


        binding.goToDatabaseB.setOnClickListener() {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToDataBaseFragment())
        }

        binding.goToFirestoreB.setOnClickListener() {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToFirestoreFragment())
        }

        binding.goToStorageB.setOnClickListener() {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToStorageFragment())
        }


        viewModel.currentUser.observe(viewLifecycleOwner) { currentUser ->
            if (currentUser.authenticated) {
                updateScreen(currentUser.firebaseUser)
            } else {
                binding.statusTV.text = "Not logged in"
            }
        }

        binding.deleteUserB.setOnClickListener() {
            viewModel.deleteUser { success, exception ->
                if (success) {
                    showToast("User deleted")
                } else {
                    showAlert(AlertType.ERROR, "Error", exception?.message.toString()) {}
                }
            }
        }

        binding.signInB.setOnClickListener() {
            val username = binding.loginET.text.toString()
            val pass = binding.passET.text.toString()

            viewModel.registerUser(username, pass) { success, exception ->
                if (success) {
                    showToast("New User created")
                } else {
                    showAlert(AlertType.ERROR, "Error", exception?.message.toString()) {}
                }
            }
        }

        binding.loginB.setOnClickListener {
            val username = binding.loginET.text.toString()
            val pass = binding.passET.text.toString()
            Log.d(TAG, "login: $username, $pass ")

            viewModel.loginUser(username, pass) { success, exception ->
                if (success) {
                    showToast("Login successful")
                } else {
                    showAlert(AlertType.ERROR, "Error", exception?.message.toString()) {}
                }
            }
        }

        binding.logOutB.setOnClickListener() {
            viewModel.logoutUser()
        }
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
        }
    }


}