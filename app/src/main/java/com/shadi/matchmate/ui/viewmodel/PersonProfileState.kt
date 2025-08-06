package com.shadi.matchmate.ui.viewmodel

import androidx.paging.PagingData
import com.shadi.matchmate.domain.model.ProfileMatch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class PersonProfileState(
    var personProfile: Flow<PagingData<ProfileMatch>> = emptyFlow(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)

