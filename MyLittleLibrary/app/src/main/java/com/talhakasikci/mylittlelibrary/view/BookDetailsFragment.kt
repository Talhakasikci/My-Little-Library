package com.talhakasikci.mylittlelibrary.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.talhakasikci.mylittlelibrary.R
import com.talhakasikci.mylittlelibrary.databinding.FragmentBookDetailsBinding
import com.talhakasikci.mylittlelibrary.model.BooksWithDetails
import com.talhakasikci.mylittlelibrary.roomdb.BooksDao
import com.talhakasikci.mylittlelibrary.viewModel.BooksViewModel


class BookDetailsFragment : Fragment() {
    private lateinit var binding:FragmentBookDetailsBinding

    private val bookDetailsViewModel: BooksViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookDetailsBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        val bookId= arguments?.getInt("bookID")?:-1
        Log.d("BookDetailsFragment", "Gelen bookId: $bookId")

        if(bookId !=-1){
            //işlem
            bookDetailsViewModel.getBookDetails(bookId).observe(viewLifecycleOwner, Observer { bookList ->
                Log.d("BookDetailsFragment", "Gelen kitap listesi: $bookList")
                if(!bookList.isNullOrEmpty()){
                    val book = bookList[0]
                    binding.bookNameDP.text=book.Book_name
                    binding.authorInfosDP.text = "${book.Author_name} ${book.Author_surname}"
                    binding.bookYearDP.text = book.Book_year.toString()
                    binding.ISBNInfoDP.text = book.ISBN.toString()
                    binding.BookTypeDP.text = book.Type
                }
            })


        }


        binding.DeleteButtonDP.setOnClickListener {
            if(bookId!=-1){
                bookDetailsViewModel.BookDeleteWithId(bookId)
                parentFragmentManager.popBackStack()
            }


        }

        return binding.root
    }


}