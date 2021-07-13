package com.example.firebasetest

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class FireBaseTest @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabaseReference: DatabaseReference
) {

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

    fun deleteUser(resultTask: (Task<Void>) -> Unit)
    {
        firebaseAuth.currentUser?.delete()?.addOnCompleteListener {
            task-> resultTask(task)
        }
    }


    fun currentUser() = firebaseAuth.currentUser


    fun addToDB(username: String, dbItem: DbItem, result: (Task<Void>) -> Unit) {
        firebaseDatabaseReference.child(username).setValue(dbItem).addOnCompleteListener { task ->
            result(task)
        }
    }


    fun addDBListener(onDataChange: (DataSnapshot) -> Unit) {
        firebaseDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                onDataChange(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

}