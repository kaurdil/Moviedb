package com.dilpreet_dtd.moviedb.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.dilpreet_dtd.moviedb.R
import com.dilpreet_dtd.moviedb.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class HomeFragment : Fragment() {
    lateinit var binding:FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(layoutInflater,container,false)
        binding.btnAddMovie.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_createFragment)
        }
        return binding.root
    }


}