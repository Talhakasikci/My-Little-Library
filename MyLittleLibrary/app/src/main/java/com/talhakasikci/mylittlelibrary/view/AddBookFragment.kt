package com.talhakasikci.mylittlelibrary.view

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

        db = Room.databaseBuilder(requireContext(),BooksDB::class.java,"Books")
            .fallbackToDestructiveMigration()
            .build()

        bookDao = db.BooksDao()
        authorsDao = db.AuthorsDao()
        typeDao = db.TypeDao()

        lifecycleScope.launch(Dispatchers.IO) {
            val authors = authorsDao.getAll()
            Log.d("RoomTest", "Yazarlar: $authors")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBookBinding.inflate(inflater,container,false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addBook.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    // Yeni yazar ve tür nesnelerini oluştur
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




                    // Yeni kitap nesnesini oluştur
                    val book = Books(
                        Author_id = authorID.toString().toInt(),
                        Book_name = binding.BookName.text.toString(),
                        Book_year = binding.BookYear.text.toString().toInt(),
                        ISBN = binding.ISBNnumber.text.toString().toLong(),
                        BookType = bookType.toInt()
                    )

                    // Kitabı veritabanına ekle
                    viewModel.insert(book)

                    Log.d("RoomTest", "Kitap eklendi: $book")

                    // Fragment'ı UI thread'de kapat
                    launch(Dispatchers.Main) {
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                } catch (e: Exception) {
                    Log.e("RoomTest", "Hata oluştu: ${e.message}")
                }
            }
        }

    }

    
}
