package com.dilpreet_dtd.moviedb.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dilpreet_dtd.moviedb.R
import com.dilpreet_dtd.moviedb.ViewModel.movieViewModel
import com.dilpreet_dtd.moviedb.databinding.FragmentSearchBinding
import com.dilpreet_dtd.moviedb.ui.Adapters.MovieAdapter
import com.dilpreet_dtd.moviedb.util.Resource


class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    val viewModel: movieViewModel by viewModels()
    lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
//        binding.searchedit.addTextChangedListener{ editable->
//                   if(editable.toString().isNotEmpty()){
//                       viewModel.getSearchMovies(editable.toString())
//                       Log.d("search",editable.toString())
//           }
//
//        }

       binding.searchview.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
           override fun onQueryTextSubmit(p0: String?): Boolean {
               return true
           }

           override fun onQueryTextChange(p0: String?): Boolean {
               p0
               return true
           }

       })
        viewModel.searchMovies.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { moviesResponse ->
                        binding.searchRcv.layoutManager =
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                        Log.d("res", moviesResponse.movies.toString())
                        binding.searchRcv.adapter = MovieAdapter(
                            requireContext(),
                            moviesResponse.movies,
                            adapter.eventlistener
                        )

                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e("error", "An error occured:$message")

                    }
                }

            }

        })
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


}