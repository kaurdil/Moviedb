package com.dilpreet_dtd.moviedb.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class convertors {
    @TypeConverter
    fun toListOfGenres(genre: String): List<Genre> {
       val listOfGenre:List<Genre> = Gson().fromJson(genre,
            object : TypeToken<List<Genre>>() {}.type)
        return listOfGenre
    }



@TypeConverter
        fun fromListOfGenres(listOfGenres: List<Genre>): String {
           return Gson().toJson(listOfGenres)
        }
    }


