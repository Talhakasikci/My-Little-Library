package com.talhakasikci.mylittlelibrary.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.talhakasikci.mylittlelibrary.model.Books
import com.talhakasikci.mylittlelibrary.model.BooksWithDetails
import com.talhakasikci.mylittlelibrary.model.Members
import com.talhakasikci.mylittlelibrary.roomdb.BooksDB
import com.talhakasikci.mylittlelibrary.roomdb.BooksDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BooksViewModel(application:Application):AndroidViewModel(application) {
    private val booksDao: BooksDao = BooksDB.getDatabase(application).BooksDao()

    val allBooksWithDetails : LiveData<List<BooksWithDetails>> = booksDao.getBooksWithDetails()
    fun insert(book: Books) {
        viewModelScope.launch(Dispatchers.IO) {
            booksDao.BookInsert(book)
        }
    }

    fun delete(book: Books) {
        viewModelScope.launch(Dispatchers.IO) {
            booksDao.BookDelete(book)
        }
    }
    fun getBookDetails(bookId: Int): LiveData<List<BooksWithDetails>> {
        return booksDao.getBookDetails(bookId)
    }

    fun BookDeleteWithId(bookId:Int){
        viewModelScope.launch(Dispatchers.IO) {
            booksDao.BookDeleteWithId(bookId)
        }
    }
}