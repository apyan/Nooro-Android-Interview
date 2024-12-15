package com.example.noorointerview.ui.element

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.noorointerview.R

@Composable
fun LocationSearchBar(
    modifier: Modifier = Modifier,
    onSearchValueChange:(searchTerm: String) -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }

    TextField (
        modifier = modifier,
        value = searchText,
        onValueChange = {
            searchText = it
            onSearchValueChange.invoke(it)
        },

        placeholder = {
            Text(
                text = stringResource(R.string.search_location)
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LocationSearchBarPreview() {
    LocationSearchBar()
}