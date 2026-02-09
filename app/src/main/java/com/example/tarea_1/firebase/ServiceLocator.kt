package com.example.tarea_1.firebase

import com.example.tarea_1.ui.AuthDataSource
import com.example.tarea_1.ui.AuthRepository

object ServiceLocator {
    val authDataSource by lazy {AuthDataSource(FirebaseProvider.provideAuth())}

    val authRepository by lazy { AuthRepository(authDataSource) }
}
