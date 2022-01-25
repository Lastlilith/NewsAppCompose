package com.example.newsappcompose.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsappcompose.MockData
import com.example.newsappcompose.MockData.getTimeAgo
import com.example.newsappcompose.R
import com.example.newsappcompose.components.SearchBar
import com.example.newsappcompose.models.TopNewsArticle
import com.example.newsappcompose.network.NewsManager
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun TopNews(
    navController: NavController,
    articles: List<TopNewsArticle>,
    query: MutableState<String>,
    newsManager: NewsManager
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        SearchBar(query = query, newsManager = newsManager)
        val searchedText = query.value
        val resultsList = mutableListOf<TopNewsArticle>()
        if (searchedText != "") {
            resultsList.addAll(newsManager.searchedNewsResponse.value.articles ?: articles)
        } else {
            resultsList.addAll(articles)
        }

        LazyColumn {
            items(articles.size) { index ->
                TopNewsItem(
                    article = resultsList[index],
                    onNewsClick = { navController.navigate("Detail/$index") })
            }
        }
    }
}

@Composable
fun TopNewsItem(article: TopNewsArticle, onNewsClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .padding(8.dp)
            .clickable {
                onNewsClick()
            }
    ) {
        CoilImage(
            imageModel = article.urlToImage,
            contentScale = ContentScale.Crop,
            error = ImageBitmap.imageResource(R.drawable.breaking_news),
            placeHolder = ImageBitmap.imageResource(R.drawable.breaking_news)
        )
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 16.dp, start = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            article.publishedAt?.let {
                Text(
                    text = MockData.stringToDate(article.publishedAt).getTimeAgo(),
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(80.dp))

            article.title?.let {
                Text(
                    text = article.title,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TopNewsPreview() {
    TopNewsItem(
        TopNewsArticle(
            title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
            description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
            publishedAt = "2021-11-04T04:42:40Z"
        )
    )
}