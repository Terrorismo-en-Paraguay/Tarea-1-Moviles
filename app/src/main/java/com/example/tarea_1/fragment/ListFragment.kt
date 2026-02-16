package com.example.tarea_1.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
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
import com.example.tarea_1.viewmodels.ListViewModelFactory

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ListViewModel

    private lateinit var bookAdapter: BookAdapter

    private val originalList = mutableListOf<Book>()
    private val _books = MutableLiveData<List<Book>>(originalList)
    val books: LiveData<List<Book>> = _books



    private fun nuevoLibro() {
        val dialogView = layoutInflater.inflate(R.layout.nuevolibro, null)
        val alertDialog = android.app.AlertDialog.Builder(requireContext()).setView(dialogView).create()
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val etTitle = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etTitle)
        val etDescription = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etDescription)
        val cbFavorite = dialogView.findViewById<CheckBox>(R.id.cbFavorite)
        val btnSubmit = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val title = etTitle.text.toString()
            val description = etDescription.text.toString()
            val fav = cbFavorite.isChecked
            if (title.isNotEmpty() && description.isNotEmpty()) {
                viewModel.saveBook(title, description, fav)
                alertDialog.dismiss()
            }else{
                Toast.makeText(requireContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        alertDialog.show()
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity(), ListViewModelFactory()).get(ListViewModel::class.java)
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
        fab?.setOnClickListener {
            nuevoLibro()
        }
    }



    private fun setupRecyclerView() {
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        bookAdapter = BookAdapter(requireActivity(), mutableListOf(), isFavFragment = false)
        binding.rv.adapter = bookAdapter
        viewModel.libros.observe(viewLifecycleOwner) { listaDeFirestore ->
            originalList.clear()
            originalList.addAll(listaDeFirestore)
            applyFilterAndSort()
        }
        viewModel.fetchBooks()

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
        bookAdapter.updateBooks(sortedList)
    }
}