package com.example.newsappcompose.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsappcompose.BottomMenuScreen
import com.example.newsappcompose.components.BottomMenu
import com.example.newsappcompose.models.TopNewsArticle
import com.example.newsappcompose.network.NewsManager
import com.example.newsappcompose.ui.screen.Categories
import com.example.newsappcompose.ui.screen.DetailScreen
import com.example.newsappcompose.ui.screen.Sources
import com.example.newsappcompose.ui.screen.TopNews

@Composable
fun NewsApp() {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    MainScreen(navController = navController, scrollState = scrollState)
}

@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState) {
    Scaffold(bottomBar = {
        BottomMenu(navController = navController)
    }) {
        Navigation(navController = navController, scrollState, paddingValues = it)
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    newsManager: NewsManager = NewsManager(),
    paddingValues: PaddingValues
) {
    val articles = newsManager.newsResponse.value.articles
    Log.d("news", "$articles")
    articles?.let {
        NavHost(
            navController = navController,
            startDestination =
            BottomMenuScreen.TopNews.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            bottomNavigation(navController = navController, articles)
            composable(
                "Detail/{index}",
                arguments = listOf(navArgument("index") { type = NavType.IntType })
            ) { navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let {
                    val article = articles[index]
                    DetailScreen(article, scrollState, navController)
                }
            }
        }
    }
}

fun NavGraphBuilder.bottomNavigation(navController: NavController, articles: List<TopNewsArticle>) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController, articles)
    }
    composable(BottomMenuScreen.Categories.route) {
        Categories()
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources()
    }
}