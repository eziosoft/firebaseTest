package com.example.firebasetest.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import com.example.firebasetest.*
import com.example.firebasetest.databinding.FragmentDataBaseBinding
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

        fireBaseTest.addDBListener() { dataSnapshot ->
            values.clear()

            for (v in dataSnapshot.children) {
                val data:DbItem? = v.getValue(DbItem::class.java)
                data?.let { values.add(it) }
            }

            adapter.notifyDataSetChanged()
        }


        binding.addToDbB.setOnClickListener() {

            val dbItem = DbItem(
                fireBaseTest.currentUser()?.email,
                binding.numberET.text.toString(),
                binding.nameET.text.toString()
            )

            fireBaseTest.currentUser()?.uid?.let { uid ->
                fireBaseTest.addToDB(uid, dbItem) { result ->
                    if (result.isSuccessful) {
                        showToast("Entry added")
                    } else {
                        showToast("Error adding entry")
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