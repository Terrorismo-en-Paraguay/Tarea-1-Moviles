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
import com.example.tarea_1.viewmodels.ListViewModelFactory

class FavFragment : Fragment() {
    private var _binding: FragmentFavBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ListViewModel
    private lateinit var bookAdapter: BookAdapter

    private var favoritesList = mutableListOf<Book>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity(), ListViewModelFactory())[ListViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookAdapter = BookAdapter(requireActivity(), mutableListOf(), isFavFragment = true)
        binding.rv.adapter = bookAdapter
        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.libros.observe(viewLifecycleOwner) { todosLosLibros ->
            favoritesList = todosLosLibros.filter { it.favourite }.toMutableList()
            applyFiltersAndSort()
        }
        viewModel.searchQuery.observe(viewLifecycleOwner) {
            applyFiltersAndSort()
        }

        viewModel.isSortAscending.observe(viewLifecycleOwner) {
            applyFiltersAndSort()
        }
    }

    override fun onResume() {
        super.onResume()
        val fab = requireActivity().findViewById<View>(R.id.fab)
        fab?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun applyFiltersAndSort() {
        val query = viewModel.searchQuery.value ?: ""
        val isAscending = viewModel.isSortAscending.value ?: true
        val filteredList = if (query.isBlank()) {
            favoritesList
        } else {
            favoritesList.filter { it.title.contains(query, ignoreCase = true) }
        }
        val sortedList = if (isAscending) {
            filteredList.sortedBy { it.title }
        } else {
            filteredList.sortedByDescending { it.title }
        }
        bookAdapter.updateBooks(sortedList)
    }

}