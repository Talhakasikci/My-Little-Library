package com.talhakasikci.mylittlelibrary.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.talhakasikci.mylittlelibrary.model.Books
import com.talhakasikci.mylittlelibrary.model.BooksWithDetails
import com.talhakasikci.mylittlelibrary.model.Members

@Dao
interface MembersDao {
    @Query("Select * from Members")
    fun getAll(): LiveData<List<Members>>



    @Query("select * from Members where id=:memberId ")
    fun getMemberDetails(memberId : Int):LiveData<List<Members>>

    @Query("delete from Members where id=:id")
    fun deleteMemberWithId(id:Int)


    @Insert
    suspend fun MemberInsert(member: Members)

    @Delete
    suspend fun MemberDelete(member: Members)
    @Update
    suspend fun updateMember(member:Members)

    @Query("select id from Members where MemberID=:memberID ")
    fun getMemberWithMemberId(memberID: Long): Int?

    @Query("select id from Members where id=:id ")
    fun getMember(id : Int):Int?
}