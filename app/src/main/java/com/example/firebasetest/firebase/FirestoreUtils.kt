package com.example.firebasetest.firebase

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.firebase.firestore.*

fun Query.addSnapshotListener(lifecycleOwner: LifecycleOwner, listener: (QuerySnapshot?, FirebaseFirestoreException?) -> Unit) {
    val registration = addSnapshotListener(listener)
    lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            registration.remove()
            lifecycleOwner.lifecycle.removeObserver(this)
        }
    })
}


fun DocumentReference.addSnapshotListener(lifecycleOwner: LifecycleOwner, listener: (DocumentSnapshot?, FirebaseFirestoreException?) -> Unit): ListenerRegistration {
    val registration = addSnapshotListener(listener)
    lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            registration.remove()
            lifecycleOwner.lifecycle.removeObserver(this)
        }

        /*
        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop() {

        }
         */
    })

    return registration
}
