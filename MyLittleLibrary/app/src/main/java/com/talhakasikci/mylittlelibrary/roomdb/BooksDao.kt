package com.talhakasikci.mylittlelibrary.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.talhakasikci.mylittlelibrary.model.Books

@Dao
interface BooksDao {

    @Query("Select * from Books")
    fun getAll(): List<Books>

    @Insert
    suspend fun BookInsert(book:Books)

    @Delete
    fun BookDelete(book:Books)
}