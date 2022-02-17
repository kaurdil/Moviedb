package com.dilpreet_dtd.moviedb.ui.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dilpreet_dtd.moviedb.R
import com.dilpreet_dtd.moviedb.databinding.ItemMoviesBinding
import com.dilpreet_dtd.moviedb.model.Movie
import com.dilpreet_dtd.moviedb.ui.fragments.HomeFragmentDirections
import com.dilpreet_dtd.moviedb.ui.fragments.SearchFragment
import com.dilpreet_dtd.moviedb.ui.fragments.SearchFragmentDirections
import com.dilpreet_dtd.moviedb.util.loadImage

class searchAdapter(
    val movielist:List<Movie>,
    val eventlistener: SearchAdapterEventListener
):RecyclerView.Adapter<searchAdapter.searchViewHolder>() {
    inner class searchViewHolder(itemView:View) :
        RecyclerView.ViewHolder(itemView){
            val title=itemView.findViewById<TextView>(R.id.title)
            val date=itemView.findViewById<TextView>(R.id.release_date)
            val img=itemView.findViewById<ImageView>(R.id.img)
            val rating =itemView.findViewById<RatingBar>(R.id.rating)
            val save=itemView.findViewById<ImageView>(R.id.save)

        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): searchViewHolder {
        return searchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.search_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: searchViewHolder, position: Int) {
        var movies = movielist[position]
        holder.title.text=movies.title
        holder.img.loadImage(movies.movieImg,false)
        holder.date.text=movies.releaseDate.toString().subSequence(0,4)
        holder.rating.rating=movies.popularity!!.toFloat()

        holder.itemView.setOnClickListener{
            val action= SearchFragmentDirections.actionSearchFragmentToDetailsFragment(movies)
            Navigation.findNavController(it).navigate(action)
        }
        holder.save.setOnClickListener{
           eventlistener.saveItem(movies)
        }

    }
interface SearchAdapterEventListener{
    fun saveItem(movie:Movie)
}

    override fun getItemCount(): Int {
        return  movielist.size

    }
}