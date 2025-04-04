package com.talhakasikci.mylittlelibrary.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.talhakasikci.mylittlelibrary.R
import com.talhakasikci.mylittlelibrary.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.booksCardView.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToBooksFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.membersCardView.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToMembersFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.rentalCardView.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToRentFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

}