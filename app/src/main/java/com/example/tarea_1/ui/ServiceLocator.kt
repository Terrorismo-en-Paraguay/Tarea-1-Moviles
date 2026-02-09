package com.example.tarea_1.ui

import com.example.tarea_1.firebase.FirebaseProvider

class ServiceLocator {
    val authDataSource by lazy {AuthDataSource(FirebaseProvider.provideAuth())}

    val authRepository by lazy { AuthRepository(authDataSource) }
}