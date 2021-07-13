package com.example.firebasetest.firebase

import com.example.firebasetest.DbItem
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

    val firebaseAuthentication = FirebaseAuthentication(firebaseAuth)

    val firebaseRealtimeDB = FirebaseRealtimeDB(firebaseDatabaseReference)

}