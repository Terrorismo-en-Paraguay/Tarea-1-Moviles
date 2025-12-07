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

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ListViewModel

    private lateinit var bookAdapter: BookAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        binding.rv.adapter?.notifyDataSetChanged()
    }

    private fun setupRecyclerView() {
        var allBooks = viewModel.getBooks().toMutableList()
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = BookAdapter(requireActivity(), allBooks, isFavFragment = false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}