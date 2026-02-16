package com.example.tarea_1.ui

import com.example.tarea_1.recycler.Book

class BookRepository (private val ds: BookDataSource) {
    fun getBooks(onResult: (List<Book>) -> Unit) {
        ds.fetchBooks(onResult)
    }
    fun addBook(title: String, desc: String, fav: Boolean) {
        ds.saveBook(title, desc, fav)
    }
    fun toggleFavorite(book: Book) {
        ds.toggleFavourite(book)
    }
}