package com.example.noorointerview

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.noorointerview.dagger.MainApplication
import com.example.noorointerview.helper.KeyboardHelper
import com.example.noorointerview.ui.element.LocationSearchBar
import com.example.noorointerview.ui.screen.LoadingScreen
import com.example.noorointerview.ui.screen.LocationInfoScreen
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
        locationSearchViewModel.loadListLocationDatabase()
        super.onCreate(savedInstanceState)

        setContent {
            val loadingStatus = locationSearchViewModel.loadingStatus.collectAsStateWithLifecycle()
            val searchTerm = locationSearchViewModel.searchTerm.collectAsStateWithLifecycle()

            NooroInterviewTheme {
                ConstraintLayout (
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val (searchBar, screenStatus) = createRefs()

                    LocationSearchBar(
                        modifier = Modifier
                            .constrainAs(searchBar) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .padding(
                                top = 44.dp,
                                start = 20.dp,
                                end = 20.dp,
                                bottom = 40.dp
                            )
                            .wrapContentHeight(),
                        searchTerm = searchTerm.value,
                        onSearchValueChange = {
                            locationSearchViewModel.setSearchTerm(it)
                            locationSearchViewModel.launchLocationSearch(
                                context = this@MainActivity,
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
                            .constrainAs(screenStatus) {
                                top.linkTo(searchBar.bottom)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }.padding(
                                top = 60.dp,
                                start = 20.dp,
                                end = 20.dp
                            ),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        when (loadingStatus.value) {
                            LoadingStatus.LOADING -> LoadingScreen()
                            LoadingStatus.SEARCH_FOUND -> {
                                SearchFoundScreen(
                                    context = this@MainActivity,
                                    locationSearchViewModel = locationSearchViewModel,
                                    clickWeatherInfo = {
                                        locationSearchViewModel.saveViewedLocationDatabase()
                                        locationSearchViewModel.setLoadingStatus(
                                            loadingStatus = LoadingStatus.LOCATION_INFO
                                        )
                                        KeyboardHelper.hideKeyboard(
                                            activity = this@MainActivity,
                                        )
                                        locationSearchViewModel.loadListLocationDatabase()
                                    }
                                )
                            }
                            LoadingStatus.SEARCH_EMPTY -> {
                                SearchEmptyScreen(
                                    context = this@MainActivity,
                                    locationSearchViewModel = locationSearchViewModel
                                )
                            }
                            LoadingStatus.LOCATION_INFO -> {
                                LocationInfoScreen(
                                    context = this@MainActivity,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}