package com.example.firebasetest.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseAuthentication(private val firebaseAuth: FirebaseAuth) {
    fun logIn(email: String, pass: String, resultTask: (Task<AuthResult>) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            resultTask(task)
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }

    fun addAuthListener(currentUser: (FirebaseUser?) -> Unit) {
        firebaseAuth.addAuthStateListener {
            currentUser(firebaseAuth.currentUser)
        }
    }

    fun registerUser(email: String, pass: String, resultTask: (Task<AuthResult>) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener() { task ->
            resultTask(task)
        }
    }

    fun deleteUser(resultTask: (Task<Void>) -> Unit) {
        firebaseAuth.currentUser?.delete()?.addOnCompleteListener { task ->
            resultTask(task)
        }
    }


    fun currentUser() = firebaseAuth.currentUser

}