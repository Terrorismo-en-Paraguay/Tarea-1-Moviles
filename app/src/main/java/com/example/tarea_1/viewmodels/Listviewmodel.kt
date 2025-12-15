package com.example.tarea_1.viewmodels

import androidx.lifecycle.ViewModel
import com.example.tarea_1.recycler.Book
import com.example.tarea_1.R

class ListViewModel : ViewModel() {

    private val _searchQuery = androidx.lifecycle.MutableLiveData<String>("")
    val searchQuery: androidx.lifecycle.MutableLiveData<String> = _searchQuery
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    private val _isSortAscending = androidx.lifecycle.MutableLiveData<Boolean>(true)
    val isSortAscending: androidx.lifecycle.LiveData<Boolean> = _isSortAscending
    fun toggleSortOrder() {
        val currentOrder = _isSortAscending.value ?: true
        _isSortAscending.value = !currentOrder
    }
    private val libros: MutableList<Book> = mutableListOf(
        Book(
            "La Odisea",
            "La Odisea es un poema épico griego compuesto por 24 cantos o rapsodias, \u200B atribuido al poeta griego Homero. Se cree que fue compuesta en el siglo VIII a. C. en los asentamientos que tenía Grecia en la costa oeste del Asia Menor. Según otros autores, la Odisea se completa en el siglo VII",
            false,
                    R.drawable.odisea
        ),
        Book(
            "Don Quijote",
            "Don Quijote de la Mancha\u200B es una novela escrita por el español Miguel de Cervantes Saavedra. Publicada su primera parte con el título de El ingenioso hidalgo don Quijote de la Mancha a comienzos de 1605, es la obra más destacada de la literatura española y una de las principales de la literatura universal",
            false,
            R.drawable.quijote
        ),
        Book(
            "Ala triste",
            "El Capitán Diego Alatriste, figura heroica de las guerras imperiales del siglo XVII, es un soldado al servicio del rey Felipe IV de España que se convierte en un mercenario al recibir el encargo de asesinar al príncipe de Gales y al duque de Buckingham.",
            false,
            R.drawable.ala_trsite
        ),
        Book(
            "Los Ojos de Plata",
            "Five Nights at Freddy's: Los ojos de plata es una novela de terror estadounidense con elementos de misterio publicada en 2015 por Scott Cawthon y Kira Breed-Wrisley, basada en la exitosa serie de videojuegos de terror Five Nights at Freddy's",
            false,
            R.drawable.ojos_de_plata
        )

    )

    fun getBooks(): List<Book> {
        return libros
    }
    fun toggleFavourite(bookTitle: String) {
        val book = libros.find { it.title == bookTitle }
        book?.favourite = book?.favourite != true
    }
}