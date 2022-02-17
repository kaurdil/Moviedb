package com.dilpreet_dtd.moviedb.ui.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.dilpreet_dtd.moviedb.R
import com.dilpreet_dtd.moviedb.ViewModel.movieViewModel
import com.dilpreet_dtd.moviedb.databinding.FragmentCreateBinding
import com.dilpreet_dtd.moviedb.model.Genre
import com.dilpreet_dtd.moviedb.model.Movie
import com.dilpreet_dtd.moviedb.util.DataUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
    var checkcounter = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateBinding.inflate(layoutInflater, container, false)
        binding.back.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_createFragment_to_homeFragment)
        }
        if (args.data == null) {
            binding.createTitle.text = "Add Movie"
            binding.btnSave.text = "CREATE"
        } else {
            binding.createTitle.text ="Edit Movie"
            binding.btnSave.text="EDIT"
        }
        binding.btnSave.setOnClickListener {
            //form validation before saving
            if (binding.names.text.isNullOrEmpty()) {
                binding.names.error = "Title required"
                return@setOnClickListener
            }
            if (!validateDate(binding.dateTxt.text.toString()) || binding.dateTxt.text.toString()
                    .isNullOrEmpty()
            ) {
                binding.dateTxt.error = "Incorrect Date"
                return@setOnClickListener
            }
            if (binding.imgUrl.text.toString().isNullOrEmpty()) {
                binding.imgUrl.error = "Url required"
                return@setOnClickListener
            }
            if (binding.langTxt.text.isNullOrEmpty()) {
                binding.langTxt.error = "Language required"
                return@setOnClickListener
            }
            if (binding.overviewTxt.text.toString().isNullOrEmpty()) {
                binding.overviewTxt.error = "Overview required"
                return@setOnClickListener
            }
            if (movie.genre.isNullOrEmpty()) {
                binding.errorTxt.text = "Plz select at least one genre!!"
                binding.errorTxt.setTextColor(Color.parseColor("#FF0000"))
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
        populategenrelist(DataUtil.getgenres())
    }

    override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
        rating = p1.toString()
    }


    private fun populategenrelist(listofgenre: List<Genre>) {

        listofgenre.forEach { genre ->
            val checkbox = CheckBox(context)
            checkbox.tag=genre.id
            checkbox.setOnCheckedChangeListener(this)
            checkbox.buttonTintList = ColorStateList.valueOf(Color.parseColor("#3e4554"));
            checkbox.text = genre.name
            currentMovie?.genre?.find { it == genre.id }?.let {
                checkbox.isChecked = true
            }
            binding.checkboxLinear.addView(checkbox)


        }

    }


    private fun validateDate(date: String): Boolean {
        val regex = "^([0-2][0-9]||3[0-1])-(0[0-9]||1[0-2])-([0-9][0-9])?[0-9][0-9]$"
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


    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        if (movie.genre.isNullOrEmpty()) {
            movie.genre = arrayListOf()
        }
        if (p1) {
            movie.genre?.add(p0?.tag.toString().toInt())
        } else {
            val newlist = arrayListOf<Int>()
            movie.genre?.filterTo(newlist, { it != p0?.id })
            movie.genre = newlist
        }


    }
}



