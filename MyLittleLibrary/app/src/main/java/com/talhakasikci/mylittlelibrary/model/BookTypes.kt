package com.talhakasikci.mylittlelibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookTypes(
    @PrimaryKey(autoGenerate = true)
    val Type_id:Int = 0,
    val Type : String

)