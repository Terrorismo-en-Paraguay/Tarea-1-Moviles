package com.example.tarea_1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.tarea_1.databinding.FragmentListBinding
import com.example.tarea_1.recycler.Book
import com.example.tarea_1.recycler.BookAdapter
import com.example.tarea_1.viewmodels.ListViewModel
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tarea_1.R

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ListViewModel

    private lateinit var bookAdapter: BookAdapter

    private val originalList = mutableListOf<Book>()
    private val _books = MutableLiveData<List<Book>>(originalList)
    val books: LiveData<List<Book>> = _books


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.searchQuery.observe(viewLifecycleOwner) { query ->
            applyFilterAndSort()
        }
        viewModel.isSortAscending.observe(viewLifecycleOwner) {
            applyFilterAndSort()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.rv.adapter?.notifyDataSetChanged()
        val fab = requireActivity().findViewById<android.view.View>(R.id.fab)
        fab?.visibility = android.view.View.VISIBLE
    }



    private fun setupRecyclerView() {
        var allBooks = viewModel.getBooks().toMutableList()
        originalList.clear()
        originalList.addAll(allBooks)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        bookAdapter = BookAdapter(requireActivity(), allBooks, isFavFragment = false)
        binding.rv.adapter = bookAdapter
        books.observe(viewLifecycleOwner) { filteredList ->
            bookAdapter.updateBooks(filteredList)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun applyFilterAndSort() {
        val query = viewModel.searchQuery.value ?: ""
        val isAscending = viewModel.isSortAscending.value ?: true
        val filteredList = if (query.isBlank()) {
            originalList
        } else {
            originalList.filter { it.title.contains(query, ignoreCase = true) }
        }
        val sortedList = if (isAscending) {
            filteredList.sortedBy { it.title }
        } else {
            filteredList.sortedByDescending { it.title }
        }
        _books.value = sortedList
    }
}