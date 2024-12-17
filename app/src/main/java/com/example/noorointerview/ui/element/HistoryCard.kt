package com.example.noorointerview.ui.element

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.noorointerview.R
import com.example.noorointerview.database.entities.SavedLocation

@Composable
fun HistoryCard(
    modifier: Modifier = Modifier,
    context: Context? = null,
    savedLocation: SavedLocation? = null
) {
    Card (
        modifier = modifier,
        shape = RoundedCornerShape(15.dp),
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column (
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(
                        start = 22.dp,
                        top = 18.dp
                    )
                    .wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                savedLocation?.name?.let {
                    Text (
                        text = it,
                        fontSize = 20.sp,
                        fontFamily = PoppinsFontMedium
                    )
                }

                Row (
                    horizontalArrangement = Arrangement.Center
                ) {
                    savedLocation?.tempF?.let {
                        Text (
                            text = String.format(stringResource(R.string.format_int), it),
                            fontSize = 60.sp,
                            fontFamily = PoppinsFontMedium
                        )
                    }

                    Image(
                        modifier = Modifier
                            .padding(
                                top = 14.dp,
                                start = 6.dp
                            )
                            .size(6.dp),
                        painter = painterResource(id = R.drawable.ic_degree_ellipse),
                        contentDescription = "Degree Icon"
                    )
                }
            }

            val imageRequest = context?.let {
                ImageRequest.Builder(it)
                    .data(savedLocation?.imageUrl)
                    .memoryCacheKey(savedLocation?.imageUrl)
                    .diskCacheKey(savedLocation?.imageUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .fallback(R.drawable.ic_launcher_foreground)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build()
            }

            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterEnd)
                    .padding(
                        end = 20.dp,
                        top = 10.dp,
                        bottom = 10.dp
                    ),
                model = imageRequest,
                contentDescription = "Weather Image",
                contentScale = ContentScale.Crop
            )
        }
        savedLocation?.lastUpdated?.let {
            Text (
                modifier = Modifier
                    .padding(
                        start = 18.dp,
                        bottom = 5.dp
                    ),
                text = String.format(stringResource(R.string.format_last_updated), it),
                fontSize = 12.sp,
                fontFamily = PoppinsFontLight
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreview() {
    HistoryCard()
}