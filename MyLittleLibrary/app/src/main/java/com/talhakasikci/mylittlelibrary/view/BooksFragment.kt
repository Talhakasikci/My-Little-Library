package com.talhakasikci.mylittlelibrary.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.talhakasikci.mylittlelibrary.R
import com.talhakasikci.mylittlelibrary.adapter.BooksViewAdapter
import com.talhakasikci.mylittlelibrary.databinding.FragmentBooksBinding
import com.talhakasikci.mylittlelibrary.viewModel.BooksViewModel
import com.talhakasikci.mylittlelibrary.viewModel.MembersViewModel

class BooksFragment : Fragment() {
  private lateinit var binding: FragmentBooksBinding
  private lateinit var adapter: BooksViewAdapter
  private lateinit var viewModel:BooksViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBooksBinding.inflate(inflater,container,false)

        val recyclerView:RecyclerView = binding.rvBooks
        adapter = BooksViewAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(BooksViewModel::class.java)

        viewModel.allBooksWithDetails.observe(viewLifecycleOwner){books->
            books?.let {
                //adaplet yap
                adapter.setBooks(it)
            }

        }
        binding.AddBookButton.setOnClickListener {
            val action = BooksFragmentDirections.actionBooksFragmentToAddBookFragment()
            Navigation.findNavController(it).navigate(action)

        }






        return binding.root
    }


}