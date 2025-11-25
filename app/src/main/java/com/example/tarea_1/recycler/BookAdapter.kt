package com.example.tarea_1.recycler

import android.content.Context
import java.util.List
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tarea_1.databinding.ItemLayoutBinding
import com.example.tarea_1.recycler.Book
import com.example.tarea_1.R

class BookAdapter (val context: Context,val items:List<Book>):  RecyclerView.Adapter<BookAdapter.BookViewHolder>(){
    private lateinit var binding: ItemLayoutBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BookViewHolder(binding)
    }
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(items[position])
    }
    override fun getItemCount(): Int {
        return items.size
    }

    class BookViewHolder(val binding : ItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.title.text = book.title
            binding.paragraph.text = book.description
            if (book.favourite){
                binding.fav.setImageResource(R.drawable.check)
            }else{
                binding.fav.setImageResource(R.drawable.nada)
            }
            binding.fav.setOnClickListener {
                if (book.favourite){
                    binding.fav.setImageResource(R.drawable.nada)
                }else{
                    binding.fav.setImageResource(R.drawable.check)
                }
                book.favourite = !book.favourite
            }
        }
    }
}

