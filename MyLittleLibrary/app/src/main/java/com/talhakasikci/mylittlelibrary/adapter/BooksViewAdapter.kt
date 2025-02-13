package com.talhakasikci.mylittlelibrary.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.talhakasikci.mylittlelibrary.R
import com.talhakasikci.mylittlelibrary.model.Books
import com.talhakasikci.mylittlelibrary.model.BooksWithDetails

class BooksViewAdapter(): RecyclerView.Adapter<BooksViewAdapter.BooksListViewHolder>() {
    private var booksList = emptyList<BooksWithDetails>()

    class BooksListViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        val BookName:TextView = itemView.findViewById(R.id.tvBookName)
        val AuthorName:TextView = itemView.findViewById(R.id.tvAuthorName)
        val AuthorSurname:TextView = itemView.findViewById(R.id.tvAuthorSurname)
        val BookId:TextView = itemView.findViewById(R.id.tvNumberBook)
}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_books_card,parent,false)

        val bookListViewHolder = BooksListViewHolder(itemView)
        return bookListViewHolder
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    override fun onBindViewHolder(holder: BooksListViewHolder, position: Int) {

        if (booksList.isEmpty()) {
            Log.e("BooksViewAdapter", "RecyclerView için veri boş!")
            return
        }
        val book = booksList[position]
        holder.BookId.text = book.Book_Id.toString()
        holder.BookName.text = book.Book_name
        holder.AuthorName.text = book.Author_name
        holder.AuthorSurname.text = book.Author_surname
    }

    fun setBooks(newBooks:List<BooksWithDetails>){
        booksList = newBooks
        notifyDataSetChanged()
    }
}


