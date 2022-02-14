package com.dilpreet_dtd.moviedb.ui.Adapters

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dilpreet_dtd.moviedb.R
import com.dilpreet_dtd.moviedb.databinding.ItemMoviesBinding
import com.dilpreet_dtd.moviedb.model.Movie
import com.dilpreet_dtd.moviedb.ui.fragments.DetailsFragment
import com.dilpreet_dtd.moviedb.ui.fragments.HomeFragment
import com.dilpreet_dtd.moviedb.ui.fragments.HomeFragmentDirections
import kotlinx.coroutines.NonDisposableHandle.parent

class MovieAdapter(
    val requireContext: Context,
    val movieList: List<Movie>,
    val eventlistener:MovieAdapterEventListener,

) : RecyclerView.Adapter<MovieAdapter.movieViewHolder>() {
    inner class movieViewHolder(val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnLongClickListener{


        override fun onLongClick(p0: View?): Boolean {
            Log.d("msg", "Long Click")
            return true
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): movieViewHolder {
        return movieViewHolder(
            ItemMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: movieViewHolder, position: Int) {
        val data = movieList[position]
        holder.binding.cardTitle.text = data.title
        holder.binding.cardDate.text = data.releaseDate
        holder.binding.cardRatingbar.rating = data.popularity?.toFloat() as (Float)
        Glide.with(requireContext).load(data.movieImg).into(holder.binding.cardimg)
        holder.binding.root.setOnLongClickListener {
            val builder = AlertDialog.Builder(requireContext)
            builder.setTitle("Delete")
            builder.setMessage("Are you Sure To Delete?")

            builder.setPositiveButton("yes") { dialog, which ->
                Toast.makeText(
                    requireContext,
                    android.R.string.yes, Toast.LENGTH_SHORT
                ).show()
                eventlistener.deleteItem(data,true)
            }

            builder.setNegativeButton("No") { dialog, which ->
                Toast.makeText(
                    requireContext,
                    android.R.string.no, Toast.LENGTH_SHORT
                ).show()
            }
            
            builder.show()
            false
        }
       holder.binding.root.setOnClickListener {
           val action=HomeFragmentDirections.actionHomeFragmentToDetailsFragment(data)
           Navigation.findNavController(it).navigate(action)
       }
        holder.binding.edit.setOnClickListener{
            val action=HomeFragmentDirections.actionHomeFragmentToCreateFragment(data)
            Navigation.findNavController(it).navigate(action)
        }


    }

    override fun getItemCount() = movieList.size
    interface MovieAdapterEventListener {
        fun deleteItem(movie: Movie, delete: Boolean)
    }
    private val differCallback=object : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem==newItem
        }

    }
   val differ=AsyncListDiffer(this,differCallback)


}















