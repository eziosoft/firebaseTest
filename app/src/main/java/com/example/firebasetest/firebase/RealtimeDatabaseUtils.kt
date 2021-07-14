package com.example.firebasetest.firebase

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

fun DatabaseReference.addValueEventListener(lifecycleOwner: LifecycleOwner, listener: ValueEventListener) {
    val registration = addValueEventListener(listener)
    lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            removeEventListener(registration)
            lifecycleOwner.lifecycle.removeObserver(this)
        }
    })
}