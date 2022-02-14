package com.dilpreet_dtd.moviedb.application

import android.app.Application
import com.facebook.stetho.Stetho

class MuvysApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
    }
}