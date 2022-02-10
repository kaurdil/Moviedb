package com.dilpreet_dtd.moviedb.model

import androidx.room.PrimaryKey

class Genre(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var name:String
)