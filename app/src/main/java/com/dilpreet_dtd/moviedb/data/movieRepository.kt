package com.dilpreet_dtd.moviedb.data

import androidx.lifecycle.LiveData
import com.dilpreet_dtd.moviedb.api.RetrofitInstance
import com.dilpreet_dtd.moviedb.dao.movieDao
import com.dilpreet_dtd.moviedb.model.Movie

class movieRepository(val dao:movieDao) {

    fun getAllMovie():LiveData<List<Movie>>{
        return dao.getMovie()
    }
    fun insertMovie(movie:Movie){
        dao.insertMovie(movie)
    }
    fun deleteMovie(id:Int){
        dao.deleteMovie(id)
    }

    fun updateMovie(movie:Movie){
        dao.updateMovie(movie)
    }
    suspend fun getSearchMovies(searchQuery:String,pageNumber:Int)=
        RetrofitInstance.api.searchMovies(searchQuery,pageNumber)
}