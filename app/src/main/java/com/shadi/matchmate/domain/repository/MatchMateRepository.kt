package com.shadi.matchmate.domain.repository

import com.shadi.matchmate.domain.model.ProfileMatch
import com.shadi.matchmate.util.Resource
import kotlinx.coroutines.flow.Flow

interface MatchMateRepository {
    suspend fun getProfileMatches( fromNetwork: Boolean): Flow<Resource<List<ProfileMatch>>>
    suspend fun updateProfileMatchStatus(userId: String, status:Int)
}