package com.talhakasikci.mylittlelibrary.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.talhakasikci.mylittlelibrary.R
import com.talhakasikci.mylittlelibrary.databinding.FragmentMemberDetailsBinding
import com.talhakasikci.mylittlelibrary.viewModel.MembersViewModel


class MemberDetailsFragment : Fragment() {
private val memberViewModel:MembersViewModel by viewModels()
    private lateinit var binding:FragmentMemberDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMemberDetailsBinding.inflate(inflater,container,false)


        val memberId=arguments?.getInt("memberId")?:-1

        if(memberId!=-1){
            memberViewModel.getMemberDetails(memberId).observe(viewLifecycleOwner,  { memberList ->
                Log.d("memberDetailsList", "Gelen üye: $memberList")
                if(!memberList.isNullOrEmpty()){
                    val member = memberList[0]
                    binding.memberNameTV.text = "${member.First_Name} ${member.Last_Name}"
                    binding.memberIdTV.text = member.MemberID.toString()
                }
            })

        }

        binding.DeleteButtonDP.setOnClickListener {
            if(memberId!=-1){
                memberViewModel.membersDeleteWithId(memberId)
                parentFragmentManager.popBackStack()
            }

        }




        return binding.root
    }


}