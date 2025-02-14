package com.talhakasikci.mylittlelibrary.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.talhakasikci.mylittlelibrary.model.Books
import com.talhakasikci.mylittlelibrary.model.Members

@Dao
interface MembersDao {
    @Query("Select * from Members")
    fun getAll(): LiveData<List<Members>>

    @Query("delete from Members where id=:id")
    fun deleteMemberWithId(id:Int)


    @Insert
    suspend fun MemberInsert(member: Members)

    @Delete
    suspend fun MemberDelete(member: Members)
}