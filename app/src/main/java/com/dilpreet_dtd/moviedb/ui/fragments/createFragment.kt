package com.dilpreet_dtd.moviedb.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.dilpreet_dtd.moviedb.R
import com.dilpreet_dtd.moviedb.databinding.FragmentCreateBinding


class createFragment : Fragment() {

    lateinit var binding:FragmentCreateBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCreateBinding.inflate(layoutInflater,container,false)
        binding.back.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_createFragment_to_homeFragment)
        }
        return binding.root
    }


}