package com.example.mynotesapp.base

import android.app.Application

class BaseApplication : Application() {

    companion object {
        var userName = ""
    }

    override fun onCreate() {
        super.onCreate()
        userName = "yk"
    }

}