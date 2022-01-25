package com.example.newsappcompose.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsappcompose.ui.MainViewModel

@Composable
fun SearchBar(query: MutableState<String>, viewModel: MainViewModel) {
    val localFocusManager = LocalFocusManager.current

    Card(
        elevation = 6.dp,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        TextField(value = query.value, onValueChange = {
            query.value = it
        }, modifier = Modifier.fillMaxWidth(), label = {

            Text(text = "Search", color = Color.White)
        },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "", tint = Color.White)
            },
            trailingIcon = {
                if (query.value != "") {
                    IconButton(onClick = {
                        query.value = ""
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "", tint = Color.White)
                    }
                }
            },
            textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (query.value != "") {
                        viewModel.getSearchedArticles(query.value)
                    }
                    localFocusManager.clearFocus()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(textColor = Color.White)
        )
    }
}