package com.shadi.matchmate.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shadi.matchmate.data.mapper.toEntity
import com.shadi.matchmate.data.mapper.toProfileMatches
import com.shadi.matchmate.domain.model.ProfileMatch


class MatchMatePagingSource(private val matchMateApi: MatchMateApi) :PagingSource<Int,ProfileMatch>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProfileMatch> {
        val page=params.key?:1
        return try{
            val response= matchMateApi.getAllProfileMatchesPaginated(count = 10,page=page)
            val data= response.let { dataDto->
                dataDto.toEntity()
                dataDto.toProfileMatches()
            }
            LoadResult.Page(
                data= data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = page+1
            )
        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProfileMatch>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}