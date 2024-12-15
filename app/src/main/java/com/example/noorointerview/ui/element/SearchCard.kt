package com.example.noorointerview.ui.element

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.noorointerview.R
import com.example.noorointerview.model.response.Current
import com.example.noorointerview.model.response.Location

@Composable
fun SearchCard(
    context: Context? = null,
    locationSearch: Location? = null,
    currentWeather: Current? = null
) {
    Card {
        Row {
            Column {
                locationSearch?.name?.let {
                    Text (text = it)
                }
                currentWeather?.tempC?.let {
                    Text (
                        text = String.format(stringResource(R.string.format_temperature_f), it)
                    )
                }
            }

            val imageRequest = context?.let {
                ImageRequest.Builder(it)
                    .data(currentWeather?.condition?.getImageUrl())
                    .memoryCacheKey(currentWeather?.condition?.getImageUrl())
                    .diskCacheKey(currentWeather?.condition?.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .fallback(R.drawable.ic_launcher_foreground)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build()
            }

            AsyncImage(
                model = imageRequest,
                contentDescription = "Movie Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(85.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchCardPreview() {
    SearchCard()
}