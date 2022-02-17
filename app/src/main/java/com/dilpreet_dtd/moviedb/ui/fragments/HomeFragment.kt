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
import com.dilpreet_dtd.moviedb.model.Genre
import com.dilpreet_dtd.moviedb.model.Movie
import com.dilpreet_dtd.moviedb.ui.Adapters.MovieAdapter
import com.dilpreet_dtd.moviedb.util.DataUtil
import com.dilpreet_dtd.moviedb.util.Resource
import com.dilpreet_dtd.moviedb.util.loadImage
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import xyz.belvi.blurhash.BlurHash


class HomeFragment : Fragment(), MovieAdapter.MovieAdapterEventListener {
    lateinit var binding: FragmentHomeBinding
    val viewModel: movieViewModel by viewModels()
    lateinit var adapter: MovieAdapter
    val json = ("{\"genres\":[{\"id\":28,\"name\":\"Action\"},{\"id\":12,\"name\":\"Adventure\"},{\"id\":16,\"name\":\"Animation\"},{\"id\":35,\"name\":\"Comedy\"},{\"id\":80,\"name\":\"Crime\"},{\"id\":99,\"name\":\"Documentary\"},{\"id\":18,\"name\":\"Drama\"},{\"id\":10751,\"name\":\"Family\"},{\"id\":14,\"name\":\"Fantasy\"},{\"id\":36,\"name\":\"History\"},{\"id\":27,\"name\":\"Horror\"},{\"id\":10402,\"name\":\"Music\"},{\"id\":9648,\"name\":\"Mystery\"},{\"id\":10749,\"name\":\"Romance\"},{\"id\":878,\"name\":\"Science Fiction\"},{\"id\":10770,\"name\":\"TV Movie\"},{\"id\":53,\"name\":\"Thriller\"},{\"id\":10752,\"name\":\"War\"},{\"id\":37,\"name\":\"Western\"}]}")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

                 binding.imgb.loadImage(viewModel.movielist.value?.getOrNull(visibleItem)?.movieImg,true)




                    }

                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }
        })

        viewModel.getMovies().observe(viewLifecycleOwner, { movieList ->
            viewModel.movielist.value = movieList.toMutableList()
            binding.recycleMovies.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.recycleMovies.adapter = MovieAdapter(requireContext(), movieList, this)

        })
        binding.searchtext.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_searchFragment)
        }
        viewModel.getGenre().observe(viewLifecycleOwner, { genreList ->
            if(genreList.isEmpty()){
                val list=DataUtil.getgenres()
                viewModel.addGenre(list)
            }
        }

        )


    }


    override fun deleteItem(movie: Movie, delete: Boolean) {
        movie.id?.let { viewModel.deleteMovie(it) }

    }


}






