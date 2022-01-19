package com.example.newsappcompose.ui

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsappcompose.MockData
import com.example.newsappcompose.ui.screen.DetailScreen
import com.example.newsappcompose.ui.screen.TopNews

@Composable
fun NewsApp() {
    Navigation()
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()

    NavHost(navController = navController, startDestination = "TopNews") {
        composable("TopNews") {
            TopNews(navController = navController)
        }
        composable("Detail/{newsId}",
        arguments = listOf(navArgument("newsId"){type = NavType.IntType})){
            navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("newsId")
            val newsData = MockData.getNews(id)
            DetailScreen(newsData, scrollState)
        }
    }
}