package com.example.tarea_1.ui

import com.example.tarea_1.recycler.Book
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class BookDataSource(private val auth: FirebaseAuth, private val db: FirebaseFirestore) {
    private val userId get() = auth.currentUser?.uid

    fun fetchBooks(onResult: (List<Book>) -> Unit) {
        val id = userId ?: return
        db.collection("usuarios").document(id).collection("libros")
            .addSnapshotListener { snapshot, _ ->
                val books = snapshot?.toObjects(Book::class.java) ?: emptyList()
                snapshot?.documents?.forEachIndexed { index, doc ->
                    books[index].id = doc.id
                }
                onResult(books)
            }
    }

    fun saveBook(title: String, desc: String, fav: Boolean) {
        val id = userId ?: return
        val map = hashMapOf("title" to title, "description" to desc, "favourite" to fav)
        db.collection("usuarios").document(id).collection("libros").add(map)
    }

    fun toggleFavourite(book: Book) {
        val id = userId ?: return
        val newStatus = !book.favourite

        db.collection("usuarios")
            .document(id)
            .collection("libros")
            .document(book.id)
            .update("favourite", newStatus)
    }

}