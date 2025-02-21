package com.talhakasikci.mylittlelibrary.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.talhakasikci.mylittlelibrary.model.Rental
import com.talhakasikci.mylittlelibrary.roomdb.BooksDB
import com.talhakasikci.mylittlelibrary.roomdb.RentalDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RentalViewModel(application: Application):AndroidViewModel(application) {
    private val rentalDao: RentalDao = BooksDB.getDatabase(application).RentalDao()

    fun addRental(rental: Rental) {
        viewModelScope.launch(Dispatchers.IO) {
            rentalDao.Insert(rental)
        }

}}