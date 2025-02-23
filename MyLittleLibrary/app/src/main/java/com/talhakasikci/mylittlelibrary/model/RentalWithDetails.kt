package com.talhakasikci.mylittlelibrary.model

import androidx.room.DatabaseView

@DatabaseView("""
    SELECT 
        r.Rental_id,
        m.First_Name AS firstName,
        m.Last_Name AS lastName,
        m.MemberID,
        b.Book_Id AS bookId,
        b.Book_name AS bookName,
        b.ISBN
    FROM Rental AS r
    INNER JOIN Members AS m ON r.Member_id = m.MemberID
    LEFT JOIN Books AS b ON r.Book_Id = b.Book_Id
""")
data class RentalWithDetails(
    val Rental_id: Int,
    val firstName: String?,
    val lastName: String?,
    val MemberID: Int,
    val bookId: Int?,
    val bookName: String?,
    val ISBN: Long?
)
