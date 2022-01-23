package com.example.newsappcompose.network

import android.util.Log
import androidx.compose.runtime.*
import com.example.newsappcompose.models.ArticleCategory
import com.example.newsappcompose.models.TopNewsResponse
import com.example.newsappcompose.models.getArticleCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsManager {

    private val _newsResponse = mutableStateOf(TopNewsResponse())
    val newsResponse: State<TopNewsResponse>
    @Composable get() = remember {
        _newsResponse
    }

    private val _getArticleByCategory = mutableStateOf(TopNewsResponse())
    val getArticleByCategory: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleByCategory
        }

    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)

    init {
        getArticles()
    }

    private fun getArticles() {
        val service = Api.retrofitService.getTopArticles("us")
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

    fun getArticlesByCategory(category: String) {
        val service = Api.retrofitService.getArticlesByCategory(category)
        service.enqueue(object : Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if(response.isSuccessful) {
                    Log.d("category", "${_getArticleByCategory.value}")
                    _getArticleByCategory.value = response.body()!!
                } else {
                    Log.d("error", "onFailure: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "onFailure: ${t.printStackTrace()}")
            }

        })
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category)
        selectedCategory.value = newCategory
    }
}