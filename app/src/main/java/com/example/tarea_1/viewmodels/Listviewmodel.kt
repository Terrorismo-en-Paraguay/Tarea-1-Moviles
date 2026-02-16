package com.example.tarea_1.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tarea_1.recycler.Book
import com.example.tarea_1.R
import com.example.tarea_1.ui.BookRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ListViewModel(private val repository: BookRepository) : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _searchQuery = androidx.lifecycle.MutableLiveData<String>("")
    val searchQuery: androidx.lifecycle.MutableLiveData<String> = _searchQuery
    private val _libros = MutableLiveData<List<Book>>()
    val libros: LiveData<List<Book>> = _libros

    fun fetchBooks() {
        repository.getBooks { lista ->
            _libros.value = lista
        }
    }

    fun saveBook(title: String, desc: String, favorito: Boolean) {
        repository.addBook(title, desc, favorito)
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private val _isSortAscending = androidx.lifecycle.MutableLiveData<Boolean>(true)
    val isSortAscending: androidx.lifecycle.LiveData<Boolean> = _isSortAscending
    fun toggleSortOrder() {
        val currentOrder = _isSortAscending.value ?: true
        _isSortAscending.value = !currentOrder
    }
    fun toggleFavourite(book: Book) {
        repository.toggleFavorite(book)
    }
}