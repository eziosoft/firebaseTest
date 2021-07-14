package com.example.firebasetest.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Singleton
    @Provides
    fun provideFirebaseRealtimeDataBase():DatabaseReference =
       Firebase.database.getReference("db")

    @Singleton
    @Provides
    fun provideFireBaseAuth() = Firebase.auth

    @Singleton
    @Provides
    fun provideFirestore() = Firebase.firestore


}