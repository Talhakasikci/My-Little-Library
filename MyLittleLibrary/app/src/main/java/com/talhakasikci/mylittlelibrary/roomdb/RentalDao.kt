package com.talhakasikci.mylittlelibrary.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.talhakasikci.mylittlelibrary.model.Rental
import com.talhakasikci.mylittlelibrary.model.RentalWithDetails

@Dao
interface RentalDao {
    @Insert
    suspend fun insert(rent: Rental)

    @Delete
    suspend fun delete(rent: Rental)

    @Query("SELECT * FROM RentalWithDetails WHERE Rental_id = :rentalId")
    fun getRentalDetails(rentalId: Int): LiveData<RentalWithDetails>

    @Query("""
        SELECT 
    m.First_Name,
    m.Last_Name,
    m.MemberID,
    b.Book_Id AS bookId,
    b.Book_name,
    b.ISBN,
    r.Rental_id 
FROM Rental AS r 
INNER JOIN members AS m ON m.MemberID = r.Member_id
LEFT JOIN Books AS b ON b.Book_Id = r.Book_Id

    """)
    fun getAllRentalDetails(): LiveData<List<RentalWithDetails>>

    @Query("delete from Rental where Rental_id =:rentalId")
    suspend fun deleteRentalWithId(rentalId: Int)
}
