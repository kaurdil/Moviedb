package com.dilpreet_dtd.moviedb.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dilpreet_dtd.moviedb.data.movieRepository
import com.dilpreet_dtd.moviedb.data.movieDatabase
import com.dilpreet_dtd.moviedb.model.Genre
import com.dilpreet_dtd.moviedb.model.Movie
import com.dilpreet_dtd.moviedb.model.MoviesResponse
import com.dilpreet_dtd.moviedb.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class movieViewModel(application: Application):AndroidViewModel(application){
    val repository: movieRepository
    val searchMovies:MutableLiveData<Resource<MoviesResponse>> = MutableLiveData()
    var moviePage=1
    val movielist=MutableLiveData<MutableList<Movie>>()
    val Genrelist=MutableLiveData<MutableList<Genre>>()

    init{
        val dao= movieDatabase.getDatabase(application).movieDao()
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
    fun getGenre() :LiveData<List<Genre>>{
        return repository.getAllgenre()
    }

    fun deleteMovie(id:Int){
        viewModelScope.launch(Dispatchers.Default) {
            repository.deleteMovie(id)
        }

    }

    fun updateMovie(movie: Movie){
        viewModelScope.launch(Dispatchers.Default) {
            repository.updateMovie(movie)
        }

    }
    fun addGenre(genre: List<Genre>){
        viewModelScope.launch(Dispatchers.Default) {
            repository.insertgenre(genre)
        }
    }

//    fun getRowCount(){
//        viewModelScope.launch(Dispatchers.Default) {
//           return repository.rowCount()
//        }
//    }
    fun getGenreByIDs(ids:List<Int>):LiveData<List<Genre>>{
           return repository.getAllgenre(ids)
    }
    fun getSearchMovies(searchQuery:String)=viewModelScope.launch (Dispatchers.IO){
        val response=repository.getSearchMovies(searchQuery,moviePage)
        Log.d("response",response.toString())
        searchMovies.postValue(handleSearchMoviesResponse(response))
    }
    private fun handleSearchMoviesResponse(response: Response<MoviesResponse>):Resource<MoviesResponse>{
       if(response.isSuccessful){
           response.body()?.let { resultResponse->
               return Resource.Success(resultResponse)
           }
       }
        return Resource.Error(response.message())
    }

}