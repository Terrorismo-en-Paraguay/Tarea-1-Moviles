package com.example.tarea_1.recycler

import com.example.tarea_1.R

data class Book(var id:String ="", val title:String="", val description:String="", var favourite:Boolean =false, val imageResId: Int = R.drawable.ic_default_book) {

}