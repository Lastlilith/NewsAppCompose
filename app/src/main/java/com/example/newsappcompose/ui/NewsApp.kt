package com.example.newsappcompose.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsappcompose.BottomMenuScreen
import com.example.newsappcompose.components.BottomMenu
import com.example.newsappcompose.models.TopNewsArticle
import com.example.newsappcompose.network.Api
import com.example.newsappcompose.network.NewsManager
import com.example.newsappcompose.ui.screen.Categories
import com.example.newsappcompose.ui.screen.DetailScreen
import com.example.newsappcompose.ui.screen.Sources
import com.example.newsappcompose.ui.screen.TopNews

@Composable
fun NewsApp(mainViewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    MainScreen(navController = navController, scrollState = scrollState, mainViewModel = mainViewModel)
}

@Composable
fun MainScreen(
    navController: NavHostController,
    scrollState: ScrollState,
    mainViewModel: MainViewModel
) {
    Scaffold(bottomBar = {
        BottomMenu(navController = navController)
    }) {
        Navigation(
            navController = navController,
            scrollState,
            paddingValues = it,
            viewModel = mainViewModel
        )
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    newsManager: NewsManager = NewsManager(Api.retrofitService),
    paddingValues: PaddingValues,
    viewModel: MainViewModel
) {
    val articles = mutableListOf(TopNewsArticle())
    val topArticles = viewModel.newsResponse.collectAsState().value.articles

    articles.addAll(topArticles ?: listOf())
    Log.d("news", "$articles")
    NavHost(
        navController = navController,
        startDestination =
        BottomMenuScreen.TopNews.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        val queryState = mutableStateOf(viewModel.query.value)

        bottomNavigation(navController = navController, articles, query = queryState, viewModel)
        composable(
            "Detail/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")
            index?.let {
                if (queryState.value != "") {
                    articles.clear()
                    articles.addAll(viewModel.searchedNewsResponse.value.articles ?: listOf())
                } else {
                    articles.clear()
                    articles.addAll(viewModel.newsResponse.value.articles ?: listOf())
                }
                val article = articles[index]
                DetailScreen(article, scrollState, navController)
            }
        }
    }
}

fun NavGraphBuilder.bottomNavigation(
    navController: NavController,
    articles: List<TopNewsArticle>,
    query: MutableState<String>,
    viewModel: MainViewModel
) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(
            navController = navController,
            articles,
            query,
            viewModel = viewModel
        )
    }
    composable(BottomMenuScreen.Categories.route) {
        viewModel.getArticlesByCategory("business")

        viewModel.onSelectedCategoryChanged("business")

        Categories(viewModel = viewModel, onFetchCategory = {
            viewModel.onSelectedCategoryChanged(it)
            viewModel.getArticlesByCategory(it)
        })
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources(viewModel = viewModel)
    }
}