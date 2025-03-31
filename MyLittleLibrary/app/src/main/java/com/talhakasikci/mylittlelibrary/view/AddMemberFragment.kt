package com.talhakasikci.mylittlelibrary.view

import android.annotation.SuppressLint
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
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.talhakasikci.mylittlelibrary.R
import com.talhakasikci.mylittlelibrary.databinding.FragmentAddMemberBinding
import com.talhakasikci.mylittlelibrary.model.Members
import com.talhakasikci.mylittlelibrary.roomdb.BooksDB
import com.talhakasikci.mylittlelibrary.roomdb.MembersDao
import com.talhakasikci.mylittlelibrary.viewModel.MembersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddMemberFragment : Fragment() {
    private lateinit var binding:FragmentAddMemberBinding
    private lateinit var memberDao :MembersDao
    private lateinit var db: BooksDB
    private lateinit var mode:String
    private var memberId = -1

    private val viewModel:MembersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddMemberBinding.inflate(inflater,container,false)
        val mode = arguments?.getString("mode") ?: "add"
        memberId = arguments?.getInt("memberId") ?: -1

        binding.apply {
            if(mode =="add"){
                addMember.visibility = View.VISIBLE
                editMember.visibility = View.GONE
            }else if(mode=="edit"){
                addMember.visibility = View.GONE
                editMember.visibility = View.VISIBLE
                editHint()
                getMemberDetails(memberId)

            }
        }

        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.addMember.setOnClickListener {
            if(checkEmpty()){
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val memberID = binding.MemberID.text.toString().toLong()
                        val memberIDColumnId = withContext(Dispatchers.IO){
                            viewModel.getMemberWithMemberID(memberID)
                        }

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

        binding.apply {
            editMember.setOnClickListener {

                hideKeyboard(it)
                if(checkEmpty()){
                    lifecycleScope.launch(Dispatchers.IO) {
                        try {
                            Log.d("editMember", "Butona basıldı!")
                            //val memberId = arguments?.getInt("memberId") ?: -1

                            val id = memberId
                            Log.d("editMember", "Güncellenecek ID: $id")





                            val member = Members(
                                id = memberId,
                                First_Name =MemberName.text.toString(),
                                Last_Name = MemberSurname.text.toString(),
                                MemberID = MemberID.text.toString().toLong()
                            )

                            Log.d("editMember", "Üye güncelleniyor: $member")
                            viewModel.updateMember(member)

                            requireActivity().runOnUiThread {
                                requireActivity().supportFragmentManager.popBackStack()
                            }
                        } catch (e: Exception) {
                            Log.e("editMember", "Hata: ${e.message}")
                        }
                    }
                }

            }

        }







    }
    private fun checkEmpty():Boolean{
        var check = true
        binding.apply {
            if(MemberName.text.isNullOrEmpty()){
                tilMemberName.error = "Please fill this area!"
                check = false
            }else
                tilMemberName.error = null
            if(MemberSurname.text.isNullOrEmpty()){
                tilMemberSurname.error = "Please fill this area!"
                check = false
            }else
                tilMemberSurname.error = null

            if(MemberID.text.isNullOrEmpty()){
                tilMemberId.error = "Please fill this area!"
                check = false
            }else
                tilMemberId.error = null


        }
        return check
    }
   private fun getMemberDetails(id: Int) {
        lifecycleScope.launch(Dispatchers.Main) {

            if(id!=-1){
                viewModel.getMemberDetails(id).observe(viewLifecycleOwner) { memberList ->
                    if (!memberList.isNullOrEmpty()) {
                        val member = memberList[0]
                        binding.MemberName.setText(member.First_Name)
                        binding.MemberSurname.setText(member.Last_Name)
                        binding.MemberID.setText(member.MemberID.toString())
                    }
                }

            }
        }
    }
    private fun editHint(){
        binding.apply {
            tilMemberName.setHint("Edit Member Name")
            tilMemberSurname.setHint("Edit Member Surname")
            tilMemberId.setHint("Edit Member ID")
        }
    }




}