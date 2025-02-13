package com.talhakasikci.mylittlelibrary.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.talhakasikci.mylittlelibrary.model.BookTypes
import com.talhakasikci.mylittlelibrary.model.Books

@Dao
interface TypeDao {

    @Query("Select * from BookTypes")
    fun getAll(): List<BookTypes>
    @Query("select Type_id from BookTypes where Type =:Type")
    fun getTypeId(Type:String):Long?

    @Insert
    suspend fun typeInsert(type: BookTypes):Long

    @Delete
    fun typeDelete(type: BookTypes)
}