package com.talhakasikci.mylittlelibrary.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.talhakasikci.mylittlelibrary.model.Authors


@Dao
interface AuthorsDao {
    @Query("Select * from Authors")
    fun getAll(): List<Authors>

    @Insert
    suspend fun authorInsert(author: Authors):Long

    @Delete
    fun BookDelete(author: Authors)
}