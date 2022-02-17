package com.dilpreet_dtd.moviedb.data

import androidx.lifecycle.LiveData
import com.dilpreet_dtd.moviedb.data.api.RetrofitInstance
import com.dilpreet_dtd.moviedb.model.Genre
import com.dilpreet_dtd.moviedb.model.Movie

class movieRepository(val dao: movieDao) {

    fun getAllMovie():LiveData<List<Movie>>{
        return dao.getMovie()
    }
    fun insertMovie(movie:Movie){
        dao.insertMovie(movie)
    }
    fun deleteMovie(id:Int){
        dao.deleteMovie(id)
    }
    fun insertgenre(genre: List<Genre>){
        dao.insertAllGenres(genre)
    }
//    fun rowCount(){
//       return dao.getRowCount()
//    }
    fun getAllgenre():LiveData<List<Genre>>{
        return dao.getGenres()
    }
    fun getAllgenre(id:List<Int>):LiveData<List<Genre>>{
        return dao.getGenreListFromId(id)
    }


    fun updateMovie(movie:Movie){
        dao.updateMovie(movie)
    }
    suspend fun getSearchMovies(searchQuery:String,pageNumber:Int)=
        RetrofitInstance.api.searchMovies(searchQuery,pageNumber)
}