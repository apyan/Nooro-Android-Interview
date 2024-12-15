package com.example.noorointerview.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.noorointerview.R

@Composable
fun SearchEmptyScreen() {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.no_city_selected)
        )
        Text(
            text = stringResource(R.string.please_search_city)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchEmptyScreenPreview() {
    SearchEmptyScreen()
}