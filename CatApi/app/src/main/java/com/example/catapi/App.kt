package com.example.catapi

import android.app.Application

class App: Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    init {
        INSTANCE = this
    }
}