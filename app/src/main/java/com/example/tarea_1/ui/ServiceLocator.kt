package com.example.tarea_1.ui

import com.example.tarea_1.firebase.FirebaseProvider
import com.google.firebase.auth.FirebaseAuth

class ServiceLocator {
    val authDataSource by lazy {AuthDataSource(FirebaseProvider.provideAuth())}

    val authRepository by lazy { AuthRepository(authDataSource) }

    val bookDataSource by lazy { BookDataSource(FirebaseAuth.getInstance(), FirebaseProvider.provideFireStore()) }

    val bookRepository by lazy { BookRepository(bookDataSource) }
}