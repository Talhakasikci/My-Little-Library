package com.talhakasikci.mylittlelibrary.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.text.toUpperCase
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.talhakasikci.mylittlelibrary.R
import com.talhakasikci.mylittlelibrary.databinding.FragmentAddBookBinding
import com.talhakasikci.mylittlelibrary.model.Authors
import com.talhakasikci.mylittlelibrary.model.BookTypes
import com.talhakasikci.mylittlelibrary.model.Books
import com.talhakasikci.mylittlelibrary.roomdb.AuthorsDao
import com.talhakasikci.mylittlelibrary.roomdb.BooksDB
import com.talhakasikci.mylittlelibrary.roomdb.BooksDao
import com.talhakasikci.mylittlelibrary.roomdb.TypeDao
import com.talhakasikci.mylittlelibrary.viewModel.BooksViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale


class AddBookFragment : Fragment() {
    private lateinit var binding: FragmentAddBookBinding

    private lateinit var db : BooksDB
    private lateinit var bookDao: BooksDao
    private lateinit var authorsDao: AuthorsDao
    private lateinit var typeDao: TypeDao
    private val viewModel:BooksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)








    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBookBinding.inflate(inflater,container,false)
        val mode = arguments?.getString("mode") ?: "add"
        val bookId = arguments?.getInt("bookId") ?: -1

        binding.apply {
            if(mode == "add"){
                addBook.visibility = View.VISIBLE
                editBook.visibility = View.GONE
            }else if (mode == "edit"){
                addBook.visibility = View.GONE
                editBook.visibility = View.VISIBLE
                editHint()
                getBookDetails(bookId)

            }
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addBook.setOnClickListener {
            if(chechEmpty()){
                lifecycleScope.launch(Dispatchers.IO) {
                    try {

                        val AuthorName = binding.AuthorName.text.toString().uppercase()
                        val AuthorSurname = binding.authorSurname.text.toString().uppercase()
                        val BookType = binding.BookType.text.toString()

                        var authorID = authorsDao.getAuthorID(AuthorName,AuthorSurname)
                        if(authorID == null){
                            val author = Authors(
                                Author_name = AuthorName,
                                Author_surname = AuthorSurname
                            )
                            authorID = authorsDao.authorInsert(author).toInt()
                        }

                        var bookType = typeDao.getTypeId(BookType)
                        if(bookType==null){
                            val type = BookTypes(
                                Type = binding.BookType.text.toString().uppercase()
                            )
                            bookType = typeDao.typeInsert(type).toLong()
                        }





                        val book = Books(
                            Author_id = authorID.toString().toInt(),
                            Book_name = binding.BookName.text.toString(),
                            Book_year = binding.BookYear.text.toString().toInt(),
                            ISBN = binding.ISBNnumber.text.toString().toLong(),
                            BookType = bookType.toInt(),
                            available = true
                        )


                        viewModel.insert(book)

                        Log.d("RoomTest", "Kitap eklendi: $book")


                        launch(Dispatchers.Main) {
                            requireActivity().supportFragmentManager.popBackStack()
                        }
                    } catch (e: Exception) {
                        Log.e("RoomTest", "Hata oluştu: ${e.message}")
                    }
                }
            }

        }

        binding.editBook.setOnClickListener {
            if(chechEmpty()){
                lifecycleScope.launch(Dispatchers.IO) {

                    try {
                        val bookId = arguments?.getInt("bookId") ?: return@launch
                        val authorName = binding.AuthorName.text.toString().uppercase()
                        val authorSurname = binding.authorSurname.text.toString().uppercase()
                        val bookTypeName = binding.BookType.text.toString().uppercase()

                        var authorID = authorsDao.getAuthorID(authorName, authorSurname)
                        if (authorID == null) {
                            val newAuthor = Authors(Author_name = authorName, Author_surname = authorSurname)
                            authorID = authorsDao.authorInsert(newAuthor).toInt()
                        }

                        var bookTypeID = typeDao.getTypeId(bookTypeName)
                        if (bookTypeID == null) {
                            val newType = BookTypes(Type = bookTypeName)
                            bookTypeID = typeDao.typeInsert(newType)
                        }

                        val updatedBook = Books(
                            Book_Id = bookId,
                            Book_name = binding.BookName.text.toString(),
                            Book_year = binding.BookYear.text.toString().toInt(),
                            ISBN = binding.ISBNnumber.text.toString().toLong(),
                            Author_id = authorID.toString().toInt(),
                            BookType = bookTypeID.toString().toInt(),
                            available = true
                        )

                        viewModel.updateBook(updatedBook)

                        // UI thread'de fragment'ı geri döndür
                        launch(Dispatchers.Main) {
                            requireActivity().supportFragmentManager.popBackStack()
                        }
                    } catch (e: Exception) {
                        Log.e("UpdateError", "Kitap güncellenirken hata oluştu: ${e.message}")
                    }
                }
            }

        }
    }
    @SuppressLint("SetTextI18n")
    private fun getBookDetails(bookId: Int) {
        viewModel.getBookDetails(bookId).observe(viewLifecycleOwner) { bookList ->
            Log.d("BookDetailsFragment", "Gelen kitap listesi: $bookList")

            if (!bookList.isNullOrEmpty()) {
                val book = bookList[0]

                binding.apply {
                    BookName.setText(book.Book_name)
                    AuthorName.setText(book.Author_name )
                    authorSurname.setText(book.Author_surname)
                    BookYear.setText(book.Book_year.toString())
                    ISBNnumber.setText(book.ISBN.toString())
                    BookType.setText(book.Type)
                }
            } else {
                Log.e("BookDetailsFragment", "Kitap listesi boş veya null!")
            }
        }
    }

    private fun chechEmpty():Boolean{
        var check = true

        binding.apply {
            if(BookName.text.isNullOrEmpty()){
                check =false
                tilBookName.error = "Please fill this area"
            }else
                tilBookName.error = null
            if(AuthorName.text.isNullOrEmpty()){
                check =false
                tilAuthorName.error = "Please fill this area"
            }else
                tilAuthorName.error = null
            if(BookYear.text.isNullOrEmpty()){
                check =false
                tilBookYear.error = "Please fill this area"
            }else
                tilBookYear.error = null
            if(BookType.text.isNullOrEmpty()){
                check =false
                tilBookType.error = "Please fill this area"
            }else
                tilBookType.error = null
            if(authorSurname.text.isNullOrEmpty()){
                check =false
                tilAuthorSurname.error = "Please fill this area"
            }else
                tilAuthorSurname.error = null
            if(ISBNnumber.text.isNullOrEmpty()){
                check =false
                tilIsbnNumber.error = "Please fill this area"
            }else
                tilIsbnNumber.error = null
        }

        return check
    }

    private fun editHint(){
        binding.apply {
            tilBookName.setHint("Edit Book Name")
            tilAuthorName.setHint("Edit Author Name")
            tilAuthorSurname.setHint("Edit Author Surname")
            tilBookType.setHint("Edir Book Type")
            tilBookYear.setHint("Edit Book Year")
            tilIsbnNumber.setHint("Edit Book ISBN")
        }
    }

    
}
