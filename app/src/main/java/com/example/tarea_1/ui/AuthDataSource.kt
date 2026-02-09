package com.example.tarea_1.ui

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthDataSource(provideAuth: FirebaseAuth) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun registrarCuenta(email: String, password: String): Result<FirebaseUser> = runCatching {
        auth.createUserWithEmailAndPassword(email, password).await().user!!
    }

    suspend fun iniciarSesion(email: String, password: String): Result<FirebaseUser> = runCatching {
        auth.signInWithEmailAndPassword(email, password).await().user!!
    }

    fun cerrarSesion() = auth.signOut()

    fun currentUser(): FirebaseUser? {
        return auth.currentUser
    }

    suspend fun resetPassword(email: String): Result<Unit> = runCatching {
        auth.sendPasswordResetEmail(email).await()
    }

}