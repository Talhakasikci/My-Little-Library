package com.talhakasikci.mylittlelibrary.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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
        b.available,
        a.Author_name,
        a.Author_surname,
        t.Type
        from Books b
        INNER JOIN Authors a ON b.Author_id = a.Author_id 
        LEFT JOIN BookTypes t ON b.BookType = t.Type_id
    """)
    fun getBooksWithDetails(): LiveData<List<BooksWithDetails>>

    @Query("""
    SELECT 
    b.Book_Id,
    b.Book_Name AS Book_name,
    b.Book_year,
    b.ISBN,
    b.available,
    a.Author_name AS Author_name,
    a.Author_surname AS Author_surname,
    t.Type AS Type
    FROM Books b
    INNER JOIN Authors a ON b.Author_id = a.Author_id 
    LEFT JOIN BookTypes t ON b.BookType = t.Type_id
    WHERE b.Book_Id = :bookId
""")
    fun getBookDetails(bookId:Int): LiveData<List<BooksWithDetails>>

    @Query("Delete from Books where Book_Id=:bookId")
    fun BookDeleteWithId(bookId: Int)

    @Insert
    suspend fun BookInsert(book: Books)

    @Delete
    fun BookDelete(book: Books)

    @Query("""
        SELECT 
        b.Book_Id,
        b.Book_Name AS Book_name,
        b.Book_year,
        b.ISBN,
        a.Author_name,
        a.Author_surname,
        t.Type,
        b.available
        FROM Books b
        INNER JOIN Authors a ON b.Author_id = a.Author_id 
        LEFT JOIN BookTypes t ON b.BookType = t.Type_id
        WHERE b.available = 1
    """)
    fun getAvailableBooks(): LiveData<List<BooksWithDetails>>

    @Query("UPDATE Books SET available = 0 WHERE Book_Id = :bookid")
    suspend fun rentBook(bookid: Int)

    @Query("UPDATE Books SET available = 1 WHERE Book_Id = :bookid")
    suspend fun returnBook(bookid: Int)

    @Update
    suspend fun updateBook(book:Books)


}
