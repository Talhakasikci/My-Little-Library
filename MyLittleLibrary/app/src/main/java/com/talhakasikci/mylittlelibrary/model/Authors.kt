package com.talhakasikci.mylittlelibrary.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Authors(
    @PrimaryKey(autoGenerate = true)
    val Author_id : Int=0,

    val Author_name:String,
    val Author_surname:String
)