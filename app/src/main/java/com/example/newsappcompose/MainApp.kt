package com.example.newsappcompose

import android.app.Application
import com.example.newsappcompose.data.Repository
import com.example.newsappcompose.network.Api
import com.example.newsappcompose.network.NewsManager

class MainApp: Application() {

    private val manager by lazy {
        NewsManager(Api.retrofitService)
    }

    val repository by lazy {
        Repository(manager = manager)
    }

}