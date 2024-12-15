package com.example.noorointerview.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noorointerview.viewmodel.LocationSearchViewModel

@Composable
fun LocationInfoScreen(
    locationSearchViewModel: LocationSearchViewModel = viewModel()
) {
}

@Preview(showBackground = true)
@Composable
fun LocationInfoScreenPreview() {
    LocationInfoScreen()
}