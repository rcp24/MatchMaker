package com.shadi.matchmate.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadi.matchmate.data.repository.MatchMateRepositoryImpl
import com.shadi.matchmate.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MatchMateViewModel @Inject constructor(private var matchMateRepository : MatchMateRepositoryImpl)  : ViewModel(){
    var state by mutableStateOf(PersonProfileState())
    private val _uiState = MutableStateFlow(PersonProfileState())
    val uiStateFlow: StateFlow<PersonProfileState> = _uiState

    val loading = mutableStateOf(false)
    init {
        getAllMatches(true)
    }
    fun getAllMatches(fromNetwork:Boolean){
        viewModelScope.launch {
            matchMateRepository.getProfileMatches(fromNetwork)
                .onEach{ result->
                    when(result) {
                        is Resource.Loading -> {
                            _uiState.value = _uiState.value.copy(isLoading = true)
                            state=state.copy(isLoading = true)
                            loading.value=true
                        }

                        is Resource.Success -> {
                            _uiState.value = _uiState.value.copy(
                                personProfile = result.data!!,
                                isLoading = false
                            )
                        state=state.copy(personProfile = result.data!!, isLoading = false)
                                println("getPersonProfile $result")
                         //   }
                            loading.value=false

                        }
                        is Resource.Error -> Unit

                    }

                }.launchIn(this)
        }

    }
    fun onProfileMatchStatusUpdated(
        userId: String,
        updatedStatus: Int,
    ) {
        viewModelScope.launch{
            matchMateRepository.updateProfileMatchStatus(userId = userId, status = updatedStatus)
            val newState= PersonProfileState()
            val updatedMatchList = _uiState.value.personProfile.map {
                if(it.userId==userId){
                    it.copy(status = updatedStatus)
                } else it
            }
            _uiState.value=_uiState.value.copy(personProfile = updatedMatchList)
        }
    }
}