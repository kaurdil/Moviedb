package com.dilpreet_dtd.moviedb.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.dilpreet_dtd.moviedb.R
import com.dilpreet_dtd.moviedb.ViewModel.movieViewModel
import com.dilpreet_dtd.moviedb.databinding.FragmentCreateBinding
import com.dilpreet_dtd.moviedb.model.Genre
import com.dilpreet_dtd.moviedb.model.Movie
import java.util.regex.Pattern


class createFragment : Fragment(), RatingBar.OnRatingBarChangeListener,
    CompoundButton.OnCheckedChangeListener {

    lateinit var binding: FragmentCreateBinding
    lateinit var radiobtn: String
    lateinit var rating: String
    lateinit var genrelist: List<Genre>
    val viewModel: movieViewModel by viewModels()
    val movie = Movie()
    private val args: createFragmentArgs by navArgs()
    var currentMovie: Movie? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateBinding.inflate(layoutInflater, container, false)
        binding.back.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_createFragment_to_homeFragment)
        }
        binding.btnSave.setOnClickListener {
            //form validation before saving
            if (binding.names.text.isNullOrEmpty()) {
                binding.names.setError("Title required")
                return@setOnClickListener
            }
            if (!validateDate(binding.dateTxt.text.toString()) || binding.dateTxt.text.toString()
                    .isNullOrEmpty()
            ) {
                binding.dateTxt.setError("Incorrect Date")
                return@setOnClickListener
            }
            if (binding.imgUrl.text.toString().isNullOrEmpty()) {
                binding.imgUrl.setError("Url required")
                return@setOnClickListener
            }
            if (binding.langTxt.text.isNullOrEmpty()) {
                binding.langTxt.setError("Language required")
                return@setOnClickListener
            }
            if (binding.overviewTxt.text.toString().isNullOrEmpty()) {
                binding.overviewTxt.setError("Overview required")
                return@setOnClickListener
            }

            if (args.data == null) {
                createMovie()
            } else {
                updateMovie(it)

            }

        }

        return binding.root
    }

    private fun updateMovie(it: View?) {
        movie.id = currentMovie?.id
        movie.title = binding.names.text.toString()
        movie.releaseDate = binding.dateTxt.text.toString()
        movie.language = binding.langTxt.text.toString()
        movie.movieImg = binding.imgUrl.text.toString()
        movie.adult = radiobtn
        movie.popularity = rating
        movie.overview = binding.overviewTxt.text.toString()
        viewModel.updateMovie(movie)
        Log.d("msg", "updated")
        activity?.onBackPressed()
        Toast.makeText(
            requireContext(),
            "Movie updated successfully", Toast.LENGTH_LONG
        ).show();

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ratingBar.onRatingBarChangeListener = this

        binding.radioGrp.setOnCheckedChangeListener { group, checkedId ->
            radiobtn = if (R.id.radioY == checkedId) "Yes" else "No"
        }

        //setting data received from the safeargs
        if (args.data != null) {
            currentMovie = args.data
            currentMovie?.let { mymovie ->
                with(binding) {
                    names.setText(mymovie.title)
                    langTxt.setText(mymovie.language)
                    dateTxt.setText(mymovie.releaseDate)
                    imgUrl.setText(mymovie.movieImg)
                }
                if (movie.adult == "Yes") {
                    binding.radioY.isChecked = true
                } else binding.radioN.isChecked = true

                binding.ratingBar.rating = mymovie.popularity!!.toFloat()
                binding.overviewTxt.setText(mymovie.overview)

            }
        }
        populategenrelist(getgenres())
    }

    override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
        rating = p1.toString()
    }


    fun populategenrelist(listofgenre: List<Genre>) {

        listofgenre.forEach {genre->
            val checkbox = CheckBox(context)


            checkbox.setOnCheckedChangeListener(this)
            checkbox.text = genre.name
            currentMovie?.genre?.find {it.name==genre.name}?.let {
                checkbox.isChecked = true
            }
            binding.checkboxLinear.addView(checkbox)
        }

    }

    fun validateDate(date: String): Boolean {
        val regex = "^(0[0-9]||1[0-2])-([0-2][0-9]||3[0-1])-([0-9][0-9])?[0-9][0-9]$"
        val matcher = Pattern.compile(regex).matcher(date)
        if (matcher.matches()) {
            return true
        }
        return false
    }


    fun createMovie() {
        movie.title = binding.names.text.toString()
        movie.releaseDate = binding.dateTxt.text.toString()
        movie.language = binding.langTxt.text.toString()
        movie.movieImg = binding.imgUrl.text.toString()
        movie.adult = radiobtn
        movie.popularity = rating
        movie.overview = binding.overviewTxt.text.toString()

        viewModel.addMovie(movie)
        activity?.onBackPressed()
        Toast.makeText(
            requireContext(),
            "Movie added successfully", Toast.LENGTH_LONG
        ).show();

    }

    fun getgenres(): List<Genre> {
        return listOf(
            Genre(1, "Comedy"),
            Genre(2, "Action"),
            Genre(3, "Drama"),
            Genre(4, "Horror"),
            Genre(5, "Romance")
        )
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        if (movie.genre.isNullOrEmpty()) {
            movie.genre = arrayListOf()
        }
        if (p1) {
            movie.genre?.add(Genre(null, p0?.text.toString()))
        } else {
            val newlist = arrayListOf<Genre>()
            movie.genre?.filterTo(newlist, { it.name != p0?.text.toString() })
            movie.genre = newlist
        }

    }
}



