package com.example.tarea_1.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tarea_1.firebase.ServiceLocator

class LoginViewModelFactory: ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Loginviewmodel::class.java)) {
            return Loginviewmodel(ServiceLocator.authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}