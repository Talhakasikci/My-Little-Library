package com.talhakasikci.mylittlelibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.talhakasikci.mylittlelibrary.R
import com.talhakasikci.mylittlelibrary.model.Members
import com.talhakasikci.mylittlelibrary.viewModel.BooksViewModel
import com.talhakasikci.mylittlelibrary.viewModel.MembersViewModel

class MembersViewAdapter(private val onItemClicked:(Int)->(Unit),
                         private val onEditClick: (Int) -> Unit,
                         private val MembersViewModel: MembersViewModel) :RecyclerView.Adapter<MembersViewAdapter.MembersListViewHolder>() {
    private var members = emptyList<Members>()


    class MembersListViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val memberOrderTV :TextView = itemView.findViewById(R.id.tvNumberItemID)
        val memberNameTv : TextView = itemView.findViewById(R.id.tvMemberName)
        val memberSurnameTv : TextView = itemView.findViewById(R.id.tvMemberSurname)
        val memberId: TextView = itemView.findViewById(R.id.tvMemberID)
        val editImg:ImageView = itemView.findViewById(R.id.ivEditButton)
        val deleteimg:ImageView = itemView.findViewById(R.id.ivDeleteButton)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersListViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_members_card,parent,false)

        val memberListViewHolder= MembersListViewHolder(itemView)
        return memberListViewHolder
    }

    override fun getItemCount(): Int {
        return members.size
    }

    override fun onBindViewHolder(holder: MembersListViewHolder, position: Int) {
        val member = members[position]
        holder.memberOrderTV.text = (holder.position+1).toString()
        holder.memberNameTv.text = member.First_Name
        holder.memberSurnameTv.text = member.Last_Name
        holder.memberId.text = member.MemberID.toString()
        holder.deleteimg.setOnClickListener {
            MembersViewModel.membersDeleteWithId(member.id)
        }
        holder.editImg.setOnClickListener {
            onEditClick(member.id)
        }
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context,"clicked ${member.id}",Toast.LENGTH_SHORT).show()
            onItemClicked(member.id)
        }
    }
    fun setMembers(newMember:List<Members>){
        members = newMember
        notifyDataSetChanged()
    }
}