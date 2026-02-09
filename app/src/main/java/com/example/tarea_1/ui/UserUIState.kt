package com.example.tarea_1.ui

import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseUser

sealed interface UserUIState {
    object Idle : UserUIState
    object Loading : UserUIState
    data class Success(val user: FirebaseUser) : UserUIState
    data class Error(val message: String) : UserUIState
}