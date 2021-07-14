package com.example.firebasetest.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.firebasetest.*
import com.example.firebasetest.databinding.FragmentFirestoreBinding
import com.example.firebasetest.firebase.FireBaseTest
import com.example.firebasetest.firebase.addSnapshotListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Error
import javax.inject.Inject


@AndroidEntryPoint
class FirestoreFragment : Fragment(R.layout.fragment_firestore) {
    private var _binding: FragmentFirestoreBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var fireBaseTest: FireBaseTest

    @Inject
    lateinit var db: FirebaseFirestore


    val docRef :DocumentReference by lazy {
        db.document("users/${fireBaseTest.firebaseAuthentication.currentUser()!!.uid}")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFirestoreBinding.bind(view)
        addFirestoreSnapshootListener()

        binding.updateB.setOnClickListener() {
            firestoreAddData()
        }

    }

    fun firestoreAddData() {
        val dbItem = DbItem(
            fireBaseTest.firebaseAuthentication.currentUser()?.email,
            binding.numberET.text.toString(),
            binding.nameET.text.toString()

        )
        docRef
            .set(dbItem)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot added with ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding document", e)
                showAlert(AlertType.ERROR,"Error",e.message.toString()){}
            }
    }




    private fun addFirestoreSnapshootListener() {
        docRef
            .addSnapshotListener(this) { documentSnapshot, firebaseFirestoreException ->
                val data = documentSnapshot?.toObject(DbItem::class.java)
                binding.userInfoTV.text = data.toString()
                Log.d(TAG, "FirestoreSnapshootListener: ${data.toString()}")

            }
    }

    fun firestoreReadData() {
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
                    val data = document.toObject(DbItem::class.java)
                    Log.d(TAG, "${document.id} => ${data.toString()}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
