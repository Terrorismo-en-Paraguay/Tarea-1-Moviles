package com.example.tarea_1.firebase

import com.google.firebase.auth.FirebaseAuth

object FirebaseProvider{
    @Volatile private var auth: FirebaseAuth? = null
    fun provideAuth(): FirebaseAuth =
        auth ?: synchronized(this) {
            auth ?: FirebaseAuth.getInstance().also { auth = it }
        }
}
