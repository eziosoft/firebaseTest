package com.example.firebasetest.firebase

import androidx.lifecycle.LifecycleOwner
import com.example.firebasetest.DbItem
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class FirebaseRealtimeDB( private val firebaseDatabaseReference: DatabaseReference) {

    fun addToDB(username: String, dbItem: DbItem, result: (Task<Void>) -> Unit) {
        firebaseDatabaseReference.child(username).setValue(dbItem).addOnCompleteListener { task ->
            result(task)
        }
    }


    fun addDBListener(lifecycleOwner: LifecycleOwner, onDataChange: (DataSnapshot) -> Unit) {
        firebaseDatabaseReference.addValueEventListener(lifecycleOwner, object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                onDataChange(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }









}