package com.dilpreet_dtd.moviedb.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dilpreet_dtd.moviedb.data.movieRepository
import com.dilpreet_dtd.moviedb.database.movieDatabase
import com.dilpreet_dtd.moviedb.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class movieViewModel(application: Application):AndroidViewModel(application){
    val repository: movieRepository
    val movielist=MutableLiveData<MutableList<Movie>>()

    init{
        val dao=movieDatabase.getDatabase(application).movieDao()
        repository= movieRepository(dao)
    }
    fun addMovie(movie: Movie){
        viewModelScope.launch(Dispatchers.Default) {
            repository.insertMovie(movie)
        }

    }
    fun getMovies() :LiveData<List<Movie>>{
        return repository.getAllMovie()
    }

    fun deleteMovie(id:Int){
        viewModelScope.launch(Dispatchers.Default) {
            repository.deleteMovie(id)
        }

    }
    fun getMovie(id:Int){

            repository.getMovie(id)
    }
    fun updateMovie(movie: Movie){
        viewModelScope.launch(Dispatchers.Default) {
            repository.updateMovie(movie)
        }

    }

}