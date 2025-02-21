package com.talhakasikci.mylittlelibrary.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.talhakasikci.mylittlelibrary.model.Members
import com.talhakasikci.mylittlelibrary.model.Rental

@Dao
interface RentalDao {
    @Insert
    suspend fun Insert(rent: Rental)

    @Delete
    suspend fun Delete(rent:Rental)

}