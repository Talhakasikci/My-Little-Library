package com.talhakasikci.mylittlelibrary.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.talhakasikci.mylittlelibrary.model.Books
import com.talhakasikci.mylittlelibrary.model.Members

@Dao
interface MembersDao {
    @Query("Select * from Members")
    fun getAll(): List<Members>

    @Insert
    fun BookInsert(member: Members)

    @Delete
    fun BookDelete(member: Members)
}