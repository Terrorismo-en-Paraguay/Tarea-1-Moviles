package com.example.tarea_1.firebase

import com.example.tarea_1.ui.AuthDataSource
import com.example.tarea_1.ui.AuthRepository
import com.example.tarea_1.ui.BookDataSource
import com.example.tarea_1.ui.BookRepository
object ServiceLocator {
    val authDataSource by lazy {AuthDataSource(FirebaseProvider.provideAuth())}

    val authRepository by lazy { AuthRepository(authDataSource) }

    val bookDataSource by lazy { BookDataSource(FirebaseProvider.provideAuth(),FirebaseProvider.provideFireStore()) }

    val bookRepository by lazy { BookRepository(bookDataSource) }
}
