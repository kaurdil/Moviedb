package com.dilpreet_dtd.moviedb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dilpreet_dtd.moviedb.model.Movie
@Dao
interface movieDao {

    @Query("Select * From Movie")
    fun getMovie(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Query("Delete From Movie where id=:id")
    fun deleteMovie(id:Int)

    @Query("SELECT * FROM Movie WHERE id=:id ")
    fun loadSingle(id: Int): LiveData<Movie>

    @Update
    fun updateMovie(movie:Movie)
}