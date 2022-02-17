package com.dilpreet_dtd.moviedb.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dilpreet_dtd.moviedb.model.Genre
import com.dilpreet_dtd.moviedb.model.Movie
import androidx.room.FtsOptions.Order

import androidx.room.OnConflictStrategy



@Dao
interface movieDao {

    @Query("Select * From Movie")
    fun getMovie(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenre(genre: Genre)

    @Query("Delete From Movie where id=:id")
    fun deleteMovie(id:Int)

//    @Query("Select count(id) FROM Genre")
//     fun getRowCount(): LiveData<Int>

    @Query("Select * From Genre")
    fun getGenres():LiveData<List<Genre>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllGenres(genre: List<Genre>)

    @Query("SELECT * FROM Genre WHERE id IN (:ids)")
    fun getGenreListFromId(ids: List<Int>): LiveData<List<Genre>>



    @Update
    fun updateMovie(movie:Movie)

}