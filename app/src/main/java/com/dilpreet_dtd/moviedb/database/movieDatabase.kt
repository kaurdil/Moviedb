package com.dilpreet_dtd.moviedb.database

import android.content.Context
import androidx.room.*
import com.dilpreet_dtd.moviedb.dao.movieDao
import com.dilpreet_dtd.moviedb.model.Movie
import com.dilpreet_dtd.moviedb.model.convertors

@Database(entities = [Movie::class],version = 1,exportSchema = false)
@TypeConverters(convertors::class)
abstract class movieDatabase :RoomDatabase() {
  abstract fun movieDao():movieDao
  companion object{
    private var INSTANCE:movieDatabase?=null
    fun getDatabase(context: Context):movieDatabase{
      @Volatile
      if(INSTANCE==null){
        synchronized(this){
          INSTANCE= Room.databaseBuilder(context.applicationContext,
            movieDatabase::class.java,"Movie").build()
        }
      }
      return INSTANCE!!

    }
  }
}