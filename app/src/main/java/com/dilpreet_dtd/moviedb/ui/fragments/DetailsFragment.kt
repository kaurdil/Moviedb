package com.dilpreet_dtd.moviedb.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.dilpreet_dtd.moviedb.ViewModel.movieViewModel
import com.dilpreet_dtd.moviedb.databinding.FragmentDetailsBinding
import com.dilpreet_dtd.moviedb.databinding.FragmentHomeBinding
import com.dilpreet_dtd.moviedb.model.Genre
import com.dilpreet_dtd.moviedb.model.Movie
import com.dilpreet_dtd.moviedb.ui.Adapters.MovieAdapter
import android.R
import android.widget.ImageView
import androidx.activity.OnBackPressedDispatcher
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.request.RequestOptions

import jp.wasabeef.glide.transformations.BlurTransformation

import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.dilpreet_dtd.moviedb.util.loadImage


class DetailsFragment : Fragment() {
    lateinit var binding: FragmentDetailsBinding
    val viewModel: movieViewModel by viewModels()
    private val safeArg: DetailsFragmentArgs by navArgs()
    var mymovie: Movie? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //receiving data using safeArgs
        val args = safeArg.data
        binding.titleTxt.text = args?.title.toString()
        binding.img.loadImage(args?.movieImg,false)
        binding.backimg.loadImage(args?.movieImg,true)

        binding.dateTxt.text = args?.releaseDate.toString()
        binding.ratingBar.rating = args?.popularity?.toFloatOrNull() ?: 0f
        // TODO: 15/02/22 change the logic to fetch the genre from genre table.find every genre by id and populate
        val gen = args?.genre?: listOf()
        viewModel.getGenreByIDs(gen).observe(viewLifecycleOwner, Observer {
            binding.genreval.text = it.map { it.name }.joinToString()

        })
        binding.languageVal.text = args?.language
        binding.adval.text = args?.adult
        binding.overtext.text = args?.overview
        binding.detailsback.setOnClickListener {
            activity?.onBackPressed()
        }
    }


}

