package com.talhakasikci.mylittlelibrary.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.talhakasikci.mylittlelibrary.model.Rental
import com.talhakasikci.mylittlelibrary.model.RentalWithDetails
import com.talhakasikci.mylittlelibrary.roomdb.BooksDB
import com.talhakasikci.mylittlelibrary.roomdb.BooksDao
import com.talhakasikci.mylittlelibrary.roomdb.RentalDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RentalViewModel(application: Application) : AndroidViewModel(application) {
    private val rentalDao: RentalDao = BooksDB.getDatabase(application).RentalDao()
    private val bookDao: BooksDao = BooksDB.getDatabase(application).BooksDao()

    fun addRental(rental: Rental) {
        viewModelScope.launch(Dispatchers.IO) {
            rentalDao.insert(rental)
        }
    }

    fun getRentalDetails(rentId: Int): LiveData<RentalWithDetails> {
        return rentalDao.getRentalDetails(rentId)
    }

    fun getAllRentalDetails(): LiveData<List<RentalWithDetails>> {
        return rentalDao.getAllRentalDetails()
    }
    fun deleteRentalWithId(bookId:Int){
        viewModelScope.launch(Dispatchers.IO) {
            rentalDao.deleteRentalWithId(bookId)
            bookDao.returnBook(bookId)
        }

    }
}
