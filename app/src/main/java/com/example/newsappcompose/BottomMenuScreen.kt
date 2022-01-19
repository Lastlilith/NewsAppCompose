package com.example.newsappcompose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Source
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomMenuScreen(val route: String, val icon: ImageVector, val title: String) {

    object TopNews: BottomMenuScreen("top news", icon = Icons.Outlined.Home, title = "Top News")

    object Categories: BottomMenuScreen("categories", icon = Icons.Outlined.Category, title = "Categories")

    object Sources: BottomMenuScreen("sources", icon = Icons.Outlined.Source, title = "Sources")
}
