package com.example.tarea_1.recycler

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.tarea_1.databinding.ItemLayoutBinding
import com.example.tarea_1.viewmodels.ListViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.FragmentActivity
import com.example.tarea_1.R

class BookAdapter(
    private val context: Context,
    private val items: List<Book>,
    private val isFavFragment: Boolean // Bandera para la lista de favoritos
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private val viewModel: ListViewModel =
        ViewModelProvider(context as FragmentActivity).get(ListViewModel::class.java)

    inner class BookViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = items[position]
        holder.binding.title.text = book.title
        holder.binding.paragraph.text = book.description

        updateFavIcon(holder.binding.fav, book.favourite)

        if (isFavFragment) {
            holder.binding.fav.isEnabled = false
        } else {
            holder.binding.fav.isEnabled = true
            holder.binding.fav.setOnClickListener {
                viewModel.toggleFavourite(book.title)
                updateFavIcon(holder.binding.fav, book.favourite)
                notifyItemChanged(position)
            }
        }
    }

    private fun updateFavIcon(icon: ImageButton, isFavourite: Boolean) {
        icon.setImageResource(
            if (isFavourite) R.drawable.check else R.drawable.nada
        )
    }
}

