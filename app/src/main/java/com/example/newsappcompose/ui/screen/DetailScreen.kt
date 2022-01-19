package com.example.newsappcompose.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsappcompose.NewsData
import com.example.newsappcompose.R

@Composable
fun DetailScreen(newsData: NewsData, scrollState: ScrollState) {
    Column(
        modifier = Modifier
            .fillMaxWidth().padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold)
        Image(painter = painterResource(id = newsData.image), contentDescription = "")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoWithIcon(icon = Icons.Default.Edit, info = newsData.author)
            InfoWithIcon(icon = Icons.Default.DateRange, info = newsData.publishedAt)
        }
        Text(text = newsData.title, fontWeight = FontWeight.Bold)
        Text(text = newsData.description, modifier = Modifier.padding(top = 16.dp))
    }
}

@Composable
fun InfoWithIcon(icon: ImageVector, info: String) {
    Row {
        Icon(
            icon,
            contentDescription = "Author",
            modifier = Modifier.padding(end = 8.dp),
            colorResource(
                id = R.color.purple_500
            )
        )
        Text(text = info)
    }
}

@Composable
@Preview(showBackground = true)
fun DetailScreenPreview() {
    DetailScreen(
        NewsData(
            2,
            R.drawable.namita,
            author = "Namita Singh",
            title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
            description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
            publishedAt = "2021-11-04T04:42:40Z"
        ),
        rememberScrollState()
    )
}