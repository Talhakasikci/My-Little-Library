package com.talhakasikci.mylittlelibrary.model

data class BooksWithDetails(
    val Book_Id: Int,
    val Book_name: String,
    val Book_year: Int,
    val ISBN: Long,
    val Author_name: String,
    val Author_surname: String,
    val Type: String? // Kitap türü NULL olabilir, bu yüzden String?
)
