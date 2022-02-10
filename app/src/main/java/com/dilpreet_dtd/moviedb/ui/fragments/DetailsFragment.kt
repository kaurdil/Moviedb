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
import com.bumptech.glide.request.RequestOptions

import jp.wasabeef.glide.transformations.BlurTransformation

import com.bumptech.glide.request.RequestOptions.bitmapTransform


class DetailsFragment : Fragment() {
    lateinit var binding: FragmentDetailsBinding
    val viewModel: movieViewModel by viewModels()
    var mymovie: Movie? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        //receiving data using bundle
        val args = this.arguments
        mymovie = args?.getParcelable<Movie>("movie")
        binding.titleTxt.text = mymovie?.title.toString()
        Glide.with(requireContext()).load(mymovie?.movieImg).into(binding.img)
        Glide.with(requireContext())
            .load(mymovie?.movieImg)
            .apply(bitmapTransform(BlurTransformation(25, 3)))
            .into(binding.backimg)
        binding.dateTxt.text = mymovie?.releaseDate.toString()
        binding.ratingBar.rating = mymovie?.popularity?.toFloat() as (Float)
        val gen = mymovie?.genre?.iterator()
        var genrelist: String = ""
        if (gen != null) {
            for (values in gen) {
                genrelist += values.name + " "
            }
        }
        binding.genreval.text = genrelist
        binding.languageVal.text = mymovie?.language
        binding.adval.text = mymovie?.adult
        binding.overtext.text = mymovie?.overview
        binding.detailsback.setOnClickListener {
            activity?.onBackPressed()
        }

        return binding.root
    }


}

