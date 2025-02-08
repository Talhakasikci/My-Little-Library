package com.talhakasikci.mylittlelibrary.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.talhakasikci.mylittlelibrary.model.Books
import com.talhakasikci.mylittlelibrary.model.BooksWithDetails

@Dao
interface BooksDao {

    @Query("""
        SELECT 
        b.Book_Id,
        b.Book_Name AS Book_name,
        b.Book_year,
        b.ISBN,
        a.Author_name,
        a.Author_surname,
        t.Type
        from Books b
        INNER JOIN Authors a ON b.Author_id = a.Author_id 
        LEFT JOIN BookTypes t ON b.BookType = t.Type_id
    """)
    fun getBooksWithDetails(): LiveData<List<BooksWithDetails>>

    @Insert
    suspend fun BookInsert(book: Books)

    @Delete
    fun BookDelete(book: Books)
}
