package com.example.newsappcompose.network

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.newsappcompose.models.TopNewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsManager {

    private val _newsResponse = mutableStateOf(TopNewsResponse())

    val newsResponse: State<TopNewsResponse>
    @Composable get() = remember {
        _newsResponse
    }

    init {
        getArticles()
    }

    private fun getArticles() {
        val service = Api.retrofitService.getTopArticles("us", Api.API_KEY)
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if(response.isSuccessful) {
                    Log.d("news", "${_newsResponse.value}")
                    _newsResponse.value = response.body()!!
                } else {
                    Log.d("error", "onFailure: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "onFailure: ${t.printStackTrace()}")
            }

        })
    }
}