package com.talhakasikci.mylittlelibrary.view

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.talhakasikci.mylittlelibrary.adapter.RentalViewAdapter
import com.talhakasikci.mylittlelibrary.databinding.FragmentRentBinding
import com.talhakasikci.mylittlelibrary.model.Rental
import com.talhakasikci.mylittlelibrary.viewModel.BooksViewModel
import com.talhakasikci.mylittlelibrary.viewModel.MembersViewModel
import com.talhakasikci.mylittlelibrary.viewModel.RentalViewModel
import kotlinx.coroutines.launch

class RentFragment : Fragment() {
    private val bookviewModel: BooksViewModel by viewModels()
    private val memberviewModel: MembersViewModel by viewModels()
    private lateinit var rentalViewModel: RentalViewModel
    private val ViewModel: RentalViewModel by viewModels()
    private lateinit var binding: FragmentRentBinding
    private lateinit var adapter: RentalViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRentBinding.inflate(inflater, container, false)

        val recyclerView:RecyclerView = binding.rvRents
        adapter = RentalViewAdapter(ViewModel)

        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
       rentalViewModel = ViewModelProvider(this).get(RentalViewModel::class.java)
        rentalViewModel.getAllRentalDetails().observe(viewLifecycleOwner) { rentals ->
            rentals?.let {
                adapter.setRentals(it)
            }
        }



        val bookMap = mutableMapOf<String, Int>()
        val autoCompleteBooks = binding.autoCompleteBooks
        bookviewModel.availableBooks.observe(viewLifecycleOwner) { books ->
            val bookNames = books.map {
                bookMap[it.Book_name] = it.Book_Id
                it.Book_name
            }
            val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, bookNames)
            autoCompleteBooks.setAdapter(adapter)
            autoCompleteBooks.threshold = 1

            autoCompleteBooks.setOnClickListener {
                autoCompleteBooks.showDropDown()
            }
        }

        val memberMap = mutableMapOf<String, Long>()
        val autoCompleteMember = binding.autoCompleteMember
        memberviewModel.allMembers.observe(viewLifecycleOwner) { members ->
            val memberInfo = members.map {
                val fullName = "${it.First_Name} ${it.Last_Name}"
                memberMap[fullName] = it.MemberID
                fullName
            }
            val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, memberInfo)
            autoCompleteMember.setAdapter(adapter)
            autoCompleteMember.threshold = 1

            autoCompleteMember.setOnClickListener {
                autoCompleteMember.showDropDown()
            }
        }

        binding.rentBookButton.setOnClickListener {
            val selectedBookName = binding.autoCompleteBooks.text.toString()
            val selectedMemberName = binding.autoCompleteMember.text.toString()

            val bookId = bookMap[selectedBookName]
            val memberID = memberMap[selectedMemberName]

            if (bookId != null && memberID != null) {
                bookviewModel.getBookDetails(bookId).observeOnce(viewLifecycleOwner) { bookDetailsList ->
                    if (bookDetailsList.isNotEmpty()) {
                        val bookDetails = bookDetailsList[0]
                        val bookISBN = bookDetails.ISBN
                        val isBookAvailable = bookDetails.available
                        if(isBookAvailable){
                            val rental = Rental(Book_Id = bookId,Member_id = memberID, Book_ISBN = bookISBN)

                            bookviewModel.viewModelScope.launch {
                                bookviewModel.rentBook(bookId)
                            }

                            rentalViewModel.addRental(rental)
                            Toast.makeText(requireContext(), "The book has been rented successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Book details not found", Toast.LENGTH_SHORT).show()
                        }
                        }


                }
            } else {
                Toast.makeText(requireContext(), "Please select a valid member/book", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    fun <T> LiveData<T>.observeOnce(
        lifecycleOwner: androidx.lifecycle.LifecycleOwner,
        observer: androidx.lifecycle.Observer<T>
    ) {
        observe(lifecycleOwner, object : androidx.lifecycle.Observer<T> {
            override fun onChanged(value: T) {
                observer.onChanged(value)
                removeObserver(this) // Gözlemciyi kaldır
            }
        })
    }
}
