package com.shadi.matchmate.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.shadi.matchmate.data.repository.MatchMateRepositoryImpl
import com.shadi.matchmate.domain.model.ProfileMatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MatchMateViewModel @Inject constructor(private var matchMateRepository : MatchMateRepositoryImpl)  : ViewModel(){
    var state by mutableStateOf(PersonProfileState())
    private val _uiState = MutableStateFlow(PersonProfileState())
    val uiStateFlow: StateFlow<PersonProfileState> = _uiState

    val items = matchMateRepository.getProfileMatchesPaginated(true).cachedIn(viewModelScope)
    fun onProfileMatchStatusUpdated(
        userId: String,
        updatedStatus: Int,
    ) {

        viewModelScope.launch{
            matchMateRepository.updateProfileMatchStatus(userId = userId, status = updatedStatus)
            val newState= PersonProfileState()
            val updatedFlow: Flow<PagingData<ProfileMatch>> = items.map { pagingData ->
                pagingData.map { item ->
                    if (item.userId == userId) {
                        item.copy(status = updatedStatus)
                    } else item
                }
            }

            _uiState.value=_uiState.value.copy(personProfile = updatedFlow)
        }
    }
}