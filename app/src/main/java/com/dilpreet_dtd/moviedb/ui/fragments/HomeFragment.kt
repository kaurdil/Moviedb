package com.dilpreet_dtd.moviedb.ui.fragments

import android.app.AlertDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.request.RequestOptions
import com.dilpreet_dtd.moviedb.R
import com.dilpreet_dtd.moviedb.ViewModel.movieViewModel
import com.dilpreet_dtd.moviedb.databinding.FragmentHomeBinding
import com.dilpreet_dtd.moviedb.model.Movie
import com.dilpreet_dtd.moviedb.ui.Adapters.MovieAdapter
import com.dilpreet_dtd.moviedb.util.Resource
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xyz.belvi.blurhash.BlurHash


class HomeFragment : Fragment(), MovieAdapter.MovieAdapterEventListener {
    lateinit var binding: FragmentHomeBinding
    val viewModel: movieViewModel by viewModels()
    lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        binding.btnadd.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_createFragment)
        }
        binding.recycleMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.layoutManager is LinearLayoutManager) {
                    val visibleItem =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (visibleItem > -1) {
                        Glide.with(requireContext())
                            .load(viewModel.movielist.value?.getOrNull(visibleItem)?.movieImg)
                            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                            .into(binding.imgb)
                    }

                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }
        })
//        binding.searchtext.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                viewModel.getSearchMovies(p0.toString())
//               return true
//
//            }
//
//            override fun onQueryTextChange(p0: String?): Boolean {
//                return true
//            }
//        })



        viewModel.getMovies().observe(viewLifecycleOwner, { movieList ->
            viewModel.movielist.value = movieList.toMutableList()
            binding.recycleMovies.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.recycleMovies.adapter = MovieAdapter(requireContext(), movieList, this)

        })
        binding.searchtext.setOnSearchClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_searchFragment)
        }

        return binding.root
    }

    override fun deleteItem(movie: Movie, delete: Boolean) {
        movie.id?.let { viewModel.deleteMovie(it) }

    }


}






