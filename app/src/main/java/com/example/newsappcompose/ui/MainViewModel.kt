package com.example.newsappcompose.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappcompose.MainApp
import com.example.newsappcompose.models.ArticleCategory
import com.example.newsappcompose.models.TopNewsResponse
import com.example.newsappcompose.models.getArticleCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = getApplication<MainApp>().repository

    private val _newsResponse = MutableStateFlow(TopNewsResponse())
    val newsResponse: StateFlow<TopNewsResponse>
        get() = _newsResponse

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getTopArticles() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _newsResponse.value = repository.getArticles()
        }
        _isLoading.value = false
    }

    private val _getArticleByCategory = MutableStateFlow(TopNewsResponse())
    val getArticleByCategory: StateFlow<TopNewsResponse>
        get() = _getArticleByCategory

    private val _selectedCategory: MutableStateFlow<ArticleCategory?> = MutableStateFlow(null)
    val selectedCategory: StateFlow<ArticleCategory?>
        get() = _selectedCategory

    fun getArticlesByCategory(category: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _getArticleByCategory.value = repository.getArticlesByCategory(category)
        }

        _isLoading.value = false
    }

    val sourceName = MutableStateFlow("engadget")
    private val _getArticleBySource = MutableStateFlow(TopNewsResponse())
    val getArticleBySource: StateFlow<TopNewsResponse>
    get() = _getArticleBySource

    val query = MutableStateFlow("")
    private val _searchedNewsResponse = MutableStateFlow(TopNewsResponse())
    val searchedNewsResponse: StateFlow<TopNewsResponse>
        get() = _searchedNewsResponse

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category)
        _selectedCategory.value = newCategory
    }

    fun getArticleBySource() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _getArticleBySource.value = repository.getArticlesBySource(sourceName.value)
        }
        _isLoading.value = false
    }

    fun getSearchedArticles(query: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _searchedNewsResponse.value = repository.getSearchedArticles(query)
        }
        _isLoading.value = false
    }

}