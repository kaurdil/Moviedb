package com.dilpreet_dtd.moviedb.data.api

import com.dilpreet_dtd.moviedb.model.MoviesResponse
import com.dilpreet_dtd.moviedb.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
     @GET("search/movie")
     suspend fun searchMovies(
          @Query("query")
          searchQuery:String,
          @Query("page") page: Int=1,
          @Query("api_key")
          api_key:String=API_KEY
          ): Response<MoviesResponse>
}