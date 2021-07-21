package com.example.firebasetest.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.firebasetest.*
import com.example.firebasetest.databinding.FragmentStorageBinding
import com.example.firebasetest.firebase.FireBaseTest
import com.example.firebasetest.utils.AlertType
import com.example.firebasetest.utils.showAlert
import com.example.firebasetest.utils.showToast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class StorageFragment : Fragment(R.layout.fragment_storage) {
    private var _binding: FragmentStorageBinding? = null
    private val binding get() = _binding!!


    private val storageRef: StorageReference by lazy {
        FirebaseStorage.getInstance().reference.child(fireBaseTest.firebaseAuthentication.currentUser()!!.uid)

    }

    @Inject
    lateinit var fireBaseTest: FireBaseTest

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStorageBinding.bind(view)

        binding.uploadImageB.setOnClickListener() {
            getImageFromGalery()
        }


        loadImageBackAndShow()

    }



    fun loadImageBackAndShow()
    {
        storageRef.downloadUrl.addOnSuccessListener { url ->
            Log.d(TAG, "getDownloadURL: $url")

            Glide
                .with(this)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_timelapse_24)
                .into(binding.imageView);
        }
        
    }
    
    fun getImageFromGalery() {
        getImage.launch("image/*")
    }

    var getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            Log.d(TAG, "URI:" + uri)

            val uploadTask = storageRef.putFile(uri)
            uploadTask.addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    showToast("Upload done")
                    Log.d(TAG, "Upload done: ")

                    loadImageBackAndShow()

                } else {
                    showAlert(AlertType.ERROR, "ERROR", task.exception?.message.toString()) {}
                    Log.e(TAG, "Upload: ", task.exception)
                }
            }

            uploadTask.addOnProgressListener { snapshot ->
                val progress: Double = (100.0
                        * snapshot.bytesTransferred
                        / snapshot.totalByteCount)

                Log.d(TAG, "onProgress: $progress")
            }

            
        }
    }
}