package com.dilpreet_dtd.moviedb.util

import android.webkit.URLUtil
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation


fun ImageView.loadImage(url: String?,isblur:Boolean) {
    val finalUrl = if (URLUtil.isValidUrl(url)) {
        url
    } else {
        "https://image.tmdb.org/t/p/w500$url"
    }
    if(!isblur) {
        Glide.with(this.context).load(finalUrl).into(this)
    }
    else {
        Glide.with(this.context).load(finalUrl)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
            .into(this)
    }

}
