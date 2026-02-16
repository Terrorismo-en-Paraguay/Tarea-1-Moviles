package com.example.tarea_1.ui

import com.google.firebase.auth.FirebaseUser

sealed interface NewUserUiState {
    object Idle : NewUserUiState
    object Loading : NewUserUiState
    data class Created(val user: FirebaseUser) : NewUserUiState
    data class Error(val message: String) : NewUserUiState
}