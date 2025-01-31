package com.talhakasikci.mylittlelibrary.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.room.RoomDatabase
import com.talhakasikci.mylittlelibrary.R
import com.talhakasikci.mylittlelibrary.databinding.ActivityAddBookBinding
import com.talhakasikci.mylittlelibrary.model.Authors
import com.talhakasikci.mylittlelibrary.model.BookTypes
import com.talhakasikci.mylittlelibrary.model.Books
import com.talhakasikci.mylittlelibrary.roomdb.AuthorsDao
import com.talhakasikci.mylittlelibrary.roomdb.BooksDB
import com.talhakasikci.mylittlelibrary.roomdb.BooksDao
import com.talhakasikci.mylittlelibrary.roomdb.TypeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddBook : AppCompatActivity() {
    private lateinit var binding:ActivityAddBookBinding
    private lateinit var db : BooksDB
    private lateinit var bookDao: BooksDao
    private lateinit var authorsDao: AuthorsDao
    private lateinit var typeDao: TypeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = Room.databaseBuilder(applicationContext,BooksDB::class.java,"Books")
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
    fun addBook(view: View) {
        lifecycleScope.launch(Dispatchers.IO) {
            // Yeni yazar ve tür nesnelerini oluştur
            val author = Authors(
                Author_name = binding.AuthorName.text.toString(),
                Author_surname = binding.authorSurname.text.toString()
            )
            val type = BookTypes(
                Type = binding.BookType.text.toString()
            )

            // Yazar ve türü veritabanına ekle ve ID'lerini al
            val authorId = authorsDao.authorInsert(author) // Geri dönen ID'yi al
            val typeId = typeDao.typeInsert(type) // Geri dönen ID'yi al

            // Yeni kitap nesnesini oluştur
            val book = Books(
                Author_id = authorId.toInt(),  // Yeni eklenen yazarın ID'si
                Book_name = binding.BookName.text.toString(),
                Book_year = binding.BookYear.text.toString().toInt(),
                ISBN = binding.ISBNnumber.text.toString().toLong(),
                BookType = typeId.toInt()  // Yeni eklenen türün ID'si
            )

            // Kitabı veritabanına ekle
            bookDao.BookInsert(book)

            Log.d("RoomTest", "Kitap eklendi: $book")
            finish()
        }
        }
    }


