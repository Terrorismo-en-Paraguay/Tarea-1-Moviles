package com.example.tarea_1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarea_1.databinding.FragmentFavBinding
import com.example.tarea_1.recycler.BookAdapter
import com.example.tarea_1.viewmodels.ListViewModel

class FavFragment : Fragment() {
    private var _binding: FragmentFavBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ListViewModel
    private lateinit var bookAdapter: BookAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favBooks = viewModel.favorites.value
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        bookAdapter = BookAdapter(requireActivity(), favBooks, isFavFragment = true)
        binding.rv.adapter = bookAdapter
    }

    override fun onResume() {
        super.onResume()
        val updatedFavBooks = viewModel.toggleFavourite()
        binding.rv.adapter = BookAdapter(requireActivity(), updatedFavBooks, isFavFragment = true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}