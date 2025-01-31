package com.talhakasikci.mylittlelibrary.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Members::class,
        parentColumns = arrayOf("MemberID"),
        childColumns = arrayOf("Member_id"),
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = Books::class,
        parentColumns = arrayOf("ISBN"),
        childColumns = arrayOf("Book_ISBN"),
        onDelete = ForeignKey.CASCADE
    )
    ]
)
data class Rental(
    @PrimaryKey(autoGenerate = true)
    val Rental_id:Int=0,
    val Member_id:Long,
    val Book_ISBN:Long

)
