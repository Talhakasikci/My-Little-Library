package com.talhakasikci.mylittlelibrary.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Authors::class,
        parentColumns = arrayOf("Author_id"),
        childColumns = arrayOf("Author_id"),
        onDelete = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = BookTypes::class,
            parentColumns = arrayOf("Type_id"),
            childColumns = arrayOf("BookType"),

        )

    ],
    indices = [Index(value = ["ISBN"], unique = true)]
)
data class Books(
    @PrimaryKey(autoGenerate = true)
    val Book_Id: Int=0,
    @ColumnInfo(name = "Book_Name")
    val Book_name: String,
    @ColumnInfo(name = "Book_year")
    val Book_year: Int,

    @ColumnInfo(name = "ISBN")
    val ISBN: Long,

    @ColumnInfo(name="available", defaultValue = "1")
    var available:Boolean,

    val Author_id:Int,
    val BookType:Int?
)