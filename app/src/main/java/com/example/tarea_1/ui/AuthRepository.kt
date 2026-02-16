package com.example.tarea_1.ui
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
class AuthRepository(private val ds: AuthDataSource) {
    suspend fun registrarse(email: String, password: String, fechaNacimiento: String): Result<FirebaseUser> = ds.registrarCuenta(email, password, fechaNacimiento)
    suspend fun loguearte(email: String, password: String): Result<FirebaseUser> = ds.iniciarSesion(email, password)
    fun signOut() = ds.cerrarSesion()
    fun currentUser(): FirebaseUser?  = ds.currentUser()
    suspend fun cambiarContrasenia(email: String): Result<Unit> = ds.resetPassword(email)
}