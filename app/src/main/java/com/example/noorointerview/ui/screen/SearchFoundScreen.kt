package com.example.noorointerview.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noorointerview.ui.element.SearchCard
import com.example.noorointerview.viewmodel.LocationSearchViewModel

@Composable
fun SearchFoundScreen(
    context: Context? = null,
    locationSearchViewModel: LocationSearchViewModel = viewModel()
) {
    val locationSearch by locationSearchViewModel.locationSearch.collectAsStateWithLifecycle()
    val currentWeather by locationSearchViewModel.currentWeather.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchCard(
            context = context,
            locationSearch = locationSearch,
            currentWeather = currentWeather
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchFoundScreenPreview() {
    SearchFoundScreen()
}