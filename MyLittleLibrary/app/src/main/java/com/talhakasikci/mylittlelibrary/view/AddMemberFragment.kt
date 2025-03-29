package com.talhakasikci.mylittlelibrary.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.talhakasikci.mylittlelibrary.R
import com.talhakasikci.mylittlelibrary.databinding.FragmentAddMemberBinding
import com.talhakasikci.mylittlelibrary.model.Members
import com.talhakasikci.mylittlelibrary.roomdb.BooksDB
import com.talhakasikci.mylittlelibrary.roomdb.MembersDao
import com.talhakasikci.mylittlelibrary.viewModel.MembersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddMemberFragment : Fragment() {
    private lateinit var binding:FragmentAddMemberBinding
    private lateinit var memberDao :MembersDao
    private lateinit var db: BooksDB
    private val viewModel:MembersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(requireContext(), BooksDB::class.java,"Books")
            .fallbackToDestructiveMigration()
            .build()

        memberDao = db.MembersDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddMemberBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.addMember.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val memberID = binding.MemberID.text.toString().toLong()
                    val memberIDColumnId = viewModel.getMemberWithID(memberID)

                    if (memberIDColumnId == null) {
                        val member = Members(
                            First_Name = binding.MemberName.text.toString(),
                            Last_Name = binding.MemberSurname.text.toString(),
                            MemberID = memberID
                        )
                        viewModel.insert(member)
                        requireActivity().runOnUiThread {
                            requireActivity().supportFragmentManager.popBackStack()
                        }
                    } else {
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "There is a member with this id: $memberID", Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e: Exception) {
                    Log.e("add member", "hata ${e.message}")
                }
            }
        }





    }


}