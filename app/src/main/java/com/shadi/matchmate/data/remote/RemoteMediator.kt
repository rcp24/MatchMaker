package com.shadi.matchmate.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shadi.matchmate.data.local.PersonProfileDataBase
import com.shadi.matchmate.data.mapper.toEntity
import com.shadi.matchmate.data.mapper.toProfileMatches
import com.shadi.matchmate.domain.model.ProfileMatch

@OptIn(ExperimentalPagingApi::class)
class MatchMateRemoteMediator(
    private val api: MatchMateApi,
    private val database: PersonProfileDataBase
) : RemoteMediator<Int, ProfileMatch>() {

    private var currentPage = 1 // count
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProfileMatch>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH ->{
                currentPage = 1
                currentPage
            }
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                currentPage++
                currentPage
            }
        }

        return try {
            val response = api.getAllProfileMatchesPaginated(count = state.config.pageSize, page = page)

            val matchEntities = response.toEntity()
            val matchList = response.toProfileMatches()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.personProfileDao.clearAllMatches()
                }
                database.personProfileDao.insertProfiles(matchEntities)
            }

            MediatorResult.Success(endOfPaginationReached = matchList.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
