package com.openclassroom.joiefull

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JoiefullApplication : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}