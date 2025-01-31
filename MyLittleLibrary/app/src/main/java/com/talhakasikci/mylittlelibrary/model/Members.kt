package com.talhakasikci.mylittlelibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Members(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val First_Name: String,
    val Last_Name: String,
    val MemberID:Long

)