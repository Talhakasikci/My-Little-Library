package com.talhakasikci.mylittlelibrary.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.talhakasikci.mylittlelibrary.R
import com.talhakasikci.mylittlelibrary.adapter.MembersViewAdapter
import com.talhakasikci.mylittlelibrary.databinding.FragmentMembersBinding
import com.talhakasikci.mylittlelibrary.viewModel.MembersViewModel

class MembersFragment : Fragment() {
    private lateinit var binding:FragmentMembersBinding
    private lateinit var membersViewModel:MembersViewModel
    private lateinit var adapter:MembersViewAdapter
    private val memberViewModel:MembersViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMembersBinding.inflate(inflater,container,false)


        val recyclerView:RecyclerView = binding.rvMember
        adapter = MembersViewAdapter(
            onItemClicked = {memberId->
          val action = MembersFragmentDirections.actionMembersFragmentToMemberDetailsFragment(memberId)
          findNavController().navigate(action)

        },
            onEditClick = {memberId->
                val action = MembersFragmentDirections.actionMembersFragmentToAddMemberFragment("edit",memberId)
                findNavController().navigate(action)

            }
            ,MembersViewModel = memberViewModel)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter



        //view Model işlemi
        membersViewModel = ViewModelProvider(this).get(MembersViewModel::class.java)

        membersViewModel.allMembers.observe(viewLifecycleOwner){members->
            members?.let {
                //adaplet yap
                adapter.setMembers(it)
            }

        }

        binding.AddMemberButton.setOnClickListener {
            val action = MembersFragmentDirections.actionMembersFragmentToAddMemberFragment()
            Navigation.findNavController(it).navigate(action)

        }
        return binding.root

    }

//burada kullanıcı listesi olacak. her kullanıcının isim soy isim ve Id bilgisi gösterilecek. kutucuğun içinde
    //güncelleme ve silme iconları olacak. silme için bir snackbar ile onay istenecek.güncelleme için belki tekrar
    //add member'a gider ve bilgileri günceller. sayfanın alt kısmında bir + butonu olmalı yeni üye için.
    //

}