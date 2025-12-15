package com.example.tarea_1.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarea_1.databinding.FragmentFavBinding
import com.example.tarea_1.recycler.Book
import com.example.tarea_1.recycler.BookAdapter
import com.example.tarea_1.viewmodels.ListViewModel
import androidx.fragment.app.activityViewModels
import com.example.tarea_1.R

class FavFragment : Fragment() {
    private var _binding: FragmentFavBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ListViewModel
    private lateinit var bookAdapter: BookAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[ListViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favBooks = viewModel.getBooks().filter { it.favourite }
        Log.d("FavFragment", "Libros favoritos encontrados: ${favBooks.size}")
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        bookAdapter = BookAdapter(requireActivity(), favBooks.toMutableList(), isFavFragment = true)
        binding.rv.adapter = bookAdapter
        viewModel.searchQuery.observe(viewLifecycleOwner) {
            applyFilters()
        }

        viewModel.isSortAscending.observe(viewLifecycleOwner) {
            applyFilters()
        }
    }

    override fun onResume() {
        super.onResume()
        val updatedFavBooks = viewModel.getBooks().filter { it.favourite }
        (binding.rv.adapter as? BookAdapter)?.updateBooks(updatedFavBooks)
        val fab = requireActivity().findViewById<android.view.View>(R.id.fab)
        fab?.visibility = android.view.View.GONE
        applyFilters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun applyFilters() {

        val allBooks = viewModel.getBooks()
        val favBooks = allBooks.filter { it.favourite }

        val query = viewModel.searchQuery.value ?: ""
        val isAscending = viewModel.isSortAscending.value ?: true

        val filteredList = if (query.isBlank()) {
            favBooks
        } else {
            favBooks.filter { it.title.contains(query, ignoreCase = true) }
        }

        val sortedList = if (isAscending) {
            filteredList.sortedBy { it.title }
        } else {
            filteredList.sortedByDescending { it.title }
        }

        bookAdapter.updateBooks(sortedList)
    }
}