package com.example.tarea_1.firebase

object ServiceLocator {
    // DataSource
    val authDataSource by lazy {AuthDataSource(FirebaseProvider.provideAuth())}
    // Repository
    val authRepository by lazy { AuthRepository(authDataSource) }
}
