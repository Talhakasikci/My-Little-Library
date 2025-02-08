package com.talhakasikci.mylittlelibrary.roomdb

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.talhakasikci.mylittlelibrary.model.Authors
import com.talhakasikci.mylittlelibrary.model.BookTypes
import com.talhakasikci.mylittlelibrary.model.Books
import com.talhakasikci.mylittlelibrary.model.Members

@Database(entities = [Books::class, Authors::class,Members::class, BookTypes::class], version = 3)
abstract class BooksDB : RoomDatabase() {
    abstract fun BooksDao(): BooksDao
    abstract fun AuthorsDao(): AuthorsDao
    abstract fun MembersDao(): MembersDao
    abstract fun TypeDao():TypeDao

    companion object {
        @Volatile
        private var INSTANCE: BooksDB? = null

        fun getDatabase(context: Context): BooksDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BooksDB::class.java,
                    "Books"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}