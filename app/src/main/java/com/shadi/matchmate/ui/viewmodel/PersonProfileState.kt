package com.shadi.matchmate.ui.viewmodel

import com.shadi.matchmate.domain.model.ProfileMatch

data class PersonProfileState(
var personProfile: List<ProfileMatch> = emptyList(),
val isLoading: Boolean = false,
val isRefreshing: Boolean = false,
)

