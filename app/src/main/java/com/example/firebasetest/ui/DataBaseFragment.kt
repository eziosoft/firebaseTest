package com.example.firebasetest.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import com.example.firebasetest.*
import com.example.firebasetest.databinding.FragmentDataBaseBinding
import com.example.firebasetest.firebase.FireBaseTest
import com.example.firebasetest.utils.AlertType
import com.example.firebasetest.utils.showAlert
import com.example.firebasetest.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DataBaseFragment : Fragment(R.layout.fragment_data_base) {
    private var _binding: FragmentDataBaseBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var fireBaseTest: FireBaseTest

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDataBaseBinding.bind(view)


        val values = arrayListOf<DbItem>()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, values)
        binding.dbListView.adapter = adapter

        fireBaseTest.firebaseRealtimeDB.addDBListener(this) { dataSnapshot ->
            values.clear()

            for (v in dataSnapshot.children) {
                val data:DbItem? = v.getValue(DbItem::class.java)
                data?.let { values.add(it) }
            }

            adapter.notifyDataSetChanged()
        }


        binding.addToDbB.setOnClickListener() {

            val dbItem = DbItem(
                fireBaseTest.firebaseAuthentication.currentUser()?.email,
                binding.numberET.text.toString(),
                binding.nameET.text.toString()
            )

            fireBaseTest.firebaseAuthentication.currentUser()?.uid?.let { uid ->
                fireBaseTest.firebaseRealtimeDB.addToDB(uid, dbItem) { result ->
                    if (result.isSuccessful) {
                        showToast("Entry added")
                    } else {
                        showAlert(AlertType.ERROR,"Error",result.exception?.message.toString()){}
                        Log.e(TAG, "add to db: ", result.exception)
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}