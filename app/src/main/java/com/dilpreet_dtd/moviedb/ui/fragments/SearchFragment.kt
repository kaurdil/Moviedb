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
import com.dilpreet_dtd.moviedb.model.Genre
import com.dilpreet_dtd.moviedb.model.Movie
import com.dilpreet_dtd.moviedb.ui.Adapters.MovieAdapter
import com.dilpreet_dtd.moviedb.ui.Adapters.searchAdapter
import com.dilpreet_dtd.moviedb.util.Resource
import java.util.Observer


class SearchFragment : Fragment() ,searchAdapter.SearchAdapterEventListener{

    lateinit var binding: FragmentSearchBinding
    val viewModel: movieViewModel by viewModels()
    lateinit var adapter: searchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.searchMovies.observe(viewLifecycleOwner) { response ->
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
                        adapter = searchAdapter(
                            moviesResponse.movies,this
                        )
                        binding.searchRcv.adapter=adapter
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e("error", "An error occured:$message")

                    }
                }

            }

        }
        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let {
                    if (it.length > 2) {
                        viewModel.getSearchMovies(it)
                    }
                }
                return true
            }
        })
        binding.back.setOnClickListener {
            activity?.onBackPressed()
        }


    }

    override fun saveItem(movie: Movie) {
        viewModel.addMovie(movie)
    }


}