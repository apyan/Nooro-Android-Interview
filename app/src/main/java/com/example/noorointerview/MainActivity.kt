package com.example.noorointerview

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.noorointerview.dagger.MainApplication
import com.example.noorointerview.ui.element.LocationSearchBar
import com.example.noorointerview.ui.screen.LoadingScreen
import com.example.noorointerview.ui.screen.LocationInfoScreen
import com.example.noorointerview.ui.screen.RecentlyFoundScreen
import com.example.noorointerview.ui.screen.SearchEmptyScreen
import com.example.noorointerview.ui.screen.SearchFoundScreen
import com.example.noorointerview.ui.theme.NooroInterviewTheme
import com.example.noorointerview.viewmodel.LoadingStatus
import com.example.noorointerview.viewmodel.LocationSearchViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<LocationSearchViewModel>
    private val locationSearchViewModel: LocationSearchViewModel by lazy {
        viewModelFactory.get<LocationSearchViewModel>(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MainApplication.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            val loadingStatus = locationSearchViewModel.loadingStatus.collectAsStateWithLifecycle()

            NooroInterviewTheme {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LocationSearchBar(
                        modifier = Modifier
                            .padding(
                                top = 44.dp,
                                start = 20.dp,
                                end = 20.dp
                            )
                            .wrapContentHeight(),
                        onSearchValueChange = {
                            android.util.Log.d("ERROR-000","Reached: " + it)
                            locationSearchViewModel.launchLocationSearch(
                                context = this@MainActivity,
                                searchTerm = it,
                                toastConnectionIssue = {
                                    Toast.makeText(
                                        this@MainActivity,
                                        getText(R.string.connection_not_found),
                                        Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    )

                    Box(
                        modifier = Modifier
                            .padding(
                                top = 20.dp,
                                start = 20.dp,
                                end = 20.dp
                            )
                            .weight(1.0f)
                    ) {
                        when (loadingStatus.value) {
                            LoadingStatus.LOADING -> LoadingScreen()
                            LoadingStatus.SEARCH_FOUND -> {
                                SearchFoundScreen(
                                    context = this@MainActivity,
                                    locationSearchViewModel = locationSearchViewModel
                                )
                            }
                            LoadingStatus.SEARCH_EMPTY -> SearchEmptyScreen()
                            LoadingStatus.RECENTLY_VIEWED -> RecentlyFoundScreen()
                            LoadingStatus.LOCATION_INFO -> LocationInfoScreen()
                        }
                    }
                }
            }
        }
    }
}