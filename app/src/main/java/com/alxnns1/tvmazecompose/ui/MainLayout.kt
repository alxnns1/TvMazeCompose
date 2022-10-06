package com.alxnns1.tvmazecompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.alxnns1.tvmazecompose.TvMazeViewModel
import com.alxnns1.tvmazecompose.model.ShowDetails
import com.alxnns1.tvmazecompose.network.ResultOf

@Composable
fun MainLayout() {
    val viewModel: TvMazeViewModel = hiltViewModel()
    val showDetailsState by viewModel.showDetailsState.collectAsState()
    var lastSearchQuery by remember { mutableStateOf("") }

    Column {
        SearchBar {
            lastSearchQuery = it
            viewModel.singleSearch(it)
        }
        when (showDetailsState) {
            is ResultOf.Success -> {
                ShowDetailsSuccess((showDetailsState as ResultOf.Success).value)
            }
            is ResultOf.Error -> {
                ShowDetailsError(lastSearchQuery)
            }
            is ResultOf.Initial -> {
                ShowDetailsInitial()
            }
        }
    }
}

@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch(searchQuery) })
        )

    }
}

@Composable
fun ShowDetailsSuccess(showDetails: ShowDetails) {
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = showDetails.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = rememberAsyncImagePainter(showDetails.imageUrl),
            contentDescription = null
        )
        val daysAgoText = if (showDetails.daysSincePremiered == 1L) {
            "${showDetails.daysSincePremiered} day"
        } else {
            "${showDetails.daysSincePremiered} days"
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = "Premiered $daysAgoText ago",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ShowDetailsError(searchQuery: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        text = "I could not find a show matching:\n\n$searchQuery\n\nPlease try another search term",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ShowDetailsInitial() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        text = "Please search for a show with the text box above",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}