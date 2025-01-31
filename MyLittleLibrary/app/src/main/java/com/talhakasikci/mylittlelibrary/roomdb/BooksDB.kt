package com.talhakasikci.mylittlelibrary.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.talhakasikci.mylittlelibrary.model.Authors
import com.talhakasikci.mylittlelibrary.model.BookTypes
import com.talhakasikci.mylittlelibrary.model.Books
import com.talhakasikci.mylittlelibrary.model.Members

@Database(entities = [Books::class, Authors::class,Members::class, BookTypes::class], version = 2)
abstract class BooksDB : RoomDatabase() {
    abstract fun BooksDao(): BooksDao
    abstract fun AuthorsDao(): AuthorsDao
    abstract fun MembersDao(): MembersDao
    abstract fun TypeDao():TypeDao
}