package com.shadi.matchmate.presentation.allmatches

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.shadi.matchmate.domain.model.ProfileMatch
import com.shadi.matchmate.presentation.components.TitleHeading
import com.shadi.matchmate.ui.viewmodel.MatchMateViewModel
import com.shadi.matchmate.ui.viewmodel.PersonProfileState
import com.shadi.matchmate.util.NetworkUtils
import kotlinx.coroutines.launch

@Composable
fun AllMatchesListingScreen(
    navController: NavHostController,
) {
    val matchMateViewModel: MatchMateViewModel = hiltViewModel()



    val uiState = matchMateViewModel.state
    val uiStateFlow =matchMateViewModel.uiStateFlow.collectAsState()

    val items = matchMateViewModel.items.collectAsLazyPagingItems()
    ProfileMatchScreenContent(
        uiState = uiStateFlow,
        onProfileStatusUpdated = { userId, updatedStatus ->
            matchMateViewModel.onProfileMatchStatusUpdated(
                userId = userId,
                updatedStatus = updatedStatus,
            )
        },
        refreshProfileMatches = {
        },
        items
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun ProfileMatchScreenContent(
    uiState: State<PersonProfileState>,
    onProfileStatusUpdated: (userId: String, updatedStatus: Int) -> Unit,
    refreshProfileMatches: () -> Unit,
    items: LazyPagingItems<ProfileMatch>,
) {


    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(value = false) }

    fun refresh() =
        refreshScope.launch {
            refreshing = true
            refreshProfileMatches()
            refreshing = false
        }

    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = ::refresh)

    Scaffold(
        modifier =
        Modifier
            .fillMaxSize()
            .background(color = Color.White),
        topBar = {
            Row(modifier = Modifier.padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                TitleHeading("MatchMate")
            }

        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .pullRefresh(state = pullRefreshState)
                .padding(top = paddingValues.calculateTopPadding()),
        ) {


                        LazyColumn(
                            Modifier
                                .background(color = Color.White)
                        ) {
                            items(items.itemCount) { idx ->
                                val item = items[idx]
                                if (item != null) {
                                    PersonProfileItem(
                                        profileMatch = item,
                                        onProfileAccepted = {
                                            onProfileStatusUpdated(items[idx]!!.userId, 1)
                                        },
                                        onProfileDeclined = {
                                            onProfileStatusUpdated(item.userId, 0)
                                        },
                                    )
                                }
                            }
                            if (items.loadState.append is LoadState.Loading) {
                                item {
                                    Box(Modifier.fillMaxWidth()) {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .align(Alignment.Center)
                                                .padding(16.dp)
                                        )
                                    }
                                }
                            }
                        }

                Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                    PullRefreshIndicator(
                        refreshing = refreshing,
                        state = pullRefreshState,
                        modifier = Modifier.align(alignment = Alignment.TopCenter),
                    )
                }
        }
    }
}



