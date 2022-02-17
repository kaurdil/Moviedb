package com.dilpreet_dtd.moviedb.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
class Genre(
    @PrimaryKey
    @NotNull
    var id: Int ,
    var name:String
)