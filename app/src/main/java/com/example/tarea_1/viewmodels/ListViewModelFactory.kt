package com.example.tarea_1.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tarea_1.firebase.ServiceLocator

class ListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListViewModel(ServiceLocator.bookRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}