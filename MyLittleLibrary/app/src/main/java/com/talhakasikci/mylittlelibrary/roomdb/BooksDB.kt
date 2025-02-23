package com.talhakasikci.mylittlelibrary.roomdb

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.talhakasikci.mylittlelibrary.model.Authors
import com.talhakasikci.mylittlelibrary.model.BookTypes
import com.talhakasikci.mylittlelibrary.model.Books
import com.talhakasikci.mylittlelibrary.model.Members
import com.talhakasikci.mylittlelibrary.model.Rental
import com.talhakasikci.mylittlelibrary.model.RentalWithDetails

@Database(entities = [Books::class, Authors::class,Members::class, BookTypes::class, Rental::class],views = [RentalWithDetails::class], version = 5)
abstract class BooksDB : RoomDatabase() {
    abstract fun BooksDao(): BooksDao
    abstract fun AuthorsDao(): AuthorsDao
    abstract fun MembersDao(): MembersDao
    abstract fun TypeDao():TypeDao
    abstract fun RentalDao():RentalDao

    companion object {
        @Volatile
        private var INSTANCE: BooksDB? = null

        fun getDatabase(context: Context): BooksDB {
            return INSTANCE ?: synchronized(this) {
                val MIGRATION_4_5 = object : Migration(4, 5) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        // Buraya eski tablolardan yeniye dönüşüm SQL sorgularını yazmalısın.
                        database.execSQL("ALTER TABLE Rental ADD COLUMN Book_ISBN INTEGER NOT NULL DEFAULT 0")
                    }
                }
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