package com.dilpreet_dtd.moviedb.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//convert a custom class to and from a known type that
// Room can persist and store the equivalent in the database
class convertors {
    @TypeConverter
    //converting from string to list
    fun toListOfGenres(genre: String): List<Genre> {
       val listOfGenre:List<Genre> = Gson().fromJson(genre,
            object : TypeToken<List<Genre>>() {}.type)
        return listOfGenre
    }



@TypeConverter
    // converting from list to String
        fun fromListOfGenres(listOfGenres: List<Genre>): String {
           return Gson().toJson(listOfGenres)
        }
    }


