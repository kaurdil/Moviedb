package com.dilpreet_dtd.moviedb.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*


@Entity(tableName = "Movie")

data class Movie(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
) : Parcelable {
    var title: String? = ""
    var releaseDate: String? = ""
    var language: String? = ""
    var genre: MutableList<Genre>? = null
    var adult: String? = ""
    var popularity: String? = ""
    var movieImg: String? = ""
    var overview: String? = ""

    constructor(parcel: Parcel) : this(parcel.readValue(Int::class.java.classLoader) as? Int) {
        title = parcel.readString()
        releaseDate = parcel.readString()
        language = parcel.readString()
        adult = parcel.readString()
        popularity = parcel.readString()
        movieImg = parcel.readString()
        overview = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(releaseDate)
        parcel.writeString(language)
        parcel.writeString(adult)
        parcel.writeString(popularity)
        parcel.writeString(movieImg)
        parcel.writeString(overview)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}
