package com.talhakasikci.mylittlelibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.talhakasikci.mylittlelibrary.R
import com.talhakasikci.mylittlelibrary.databinding.ItemViewRentalCardBinding
import com.talhakasikci.mylittlelibrary.model.RentalWithDetails
import com.talhakasikci.mylittlelibrary.viewModel.RentalViewModel

class RentalViewAdapter(private val rentalViewModel:RentalViewModel):RecyclerView.Adapter<RentalViewAdapter.ViewHolder>() {
    private lateinit var binding:ItemViewRentalCardBinding

    private var rentList = emptyList<RentalWithDetails>()

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val userName:TextView = itemView.findViewById(R.id.tvMemberName)
        val userSurname:TextView = itemView.findViewById(R.id.tvMemberSurname)
        val rentalId:TextView = itemView.findViewById(R.id.tvNumberItemID)
        val bookName:TextView = itemView.findViewById(R.id.tvBookName)
        val delteIcon:ImageView = itemView.findViewById(R.id.ivDeleteButton)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_rental_card,parent,false)

        val RentalviewHolder= ViewHolder(itemView)
        return RentalviewHolder
    }

    override fun getItemCount(): Int {
        return rentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(!rentList.isEmpty()){
            val rent = rentList[position]
            holder.rentalId.text = rent.Rental_id.toString()

            rentalViewModel.getRentalDetails(rent.Rental_id).observeForever { rental ->
                holder.userName.text = rental?.firstName ?: "Bilinmiyor"
                holder.userSurname.text = rental?.lastName ?: "Bilinmiyor"
                holder.bookName.text = rental?.bookName?: "Bilinmiyor"
                holder.delteIcon.setOnClickListener {
                    rentalViewModel.deleteRentalWithId(rent.Rental_id)
                }
            }
        }
    }
    fun setRentals(newRent: List<RentalWithDetails>) {
        rentList = newRent
        notifyDataSetChanged()
    }
}