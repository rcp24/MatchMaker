package com.shadi.matchmate.domain.repository

import androidx.paging.PagingData
import com.shadi.matchmate.domain.model.ProfileMatch
import com.shadi.matchmate.util.Resource
import kotlinx.coroutines.flow.Flow

interface MatchMateRepository {
    suspend fun getProfileMatches( fromNetwork: Boolean): Flow<Resource<List<ProfileMatch>>>
    fun getProfileMatchesPaginated( fromNetwork: Boolean): Flow<PagingData<ProfileMatch>>
    suspend fun updateProfileMatchStatus(userId: String, status:Int)
}