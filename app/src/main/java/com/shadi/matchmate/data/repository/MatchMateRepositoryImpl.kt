package com.shadi.matchmate.data.repository


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shadi.matchmate.data.local.PersonProfileDataBase
import com.shadi.matchmate.data.mapper.toEntity
import com.shadi.matchmate.data.remote.MatchMateApi
import com.shadi.matchmate.data.remote.MatchMateRemoteMediator
import com.shadi.matchmate.domain.model.ProfileMatch
import com.shadi.matchmate.domain.repository.MatchMateRepository
import com.shadi.matchmate.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MatchMateRepositoryImpl @Inject constructor(
    private val matchMateApi: MatchMateApi,
    private val db: PersonProfileDataBase
) : MatchMateRepository {

    override suspend fun getProfileMatches(
        fromNetwork: Boolean
    ): Flow<Resource<List<ProfileMatch>>> {
       return flow {
           val remoteListings = try {
               matchMateApi.getAllProfileMatches(10)
           } catch (e: IOException) {
               e.printStackTrace()
               emit(Resource.Error("Couldn't load data"))
               null
           } catch (e: HttpException) {
               e.printStackTrace()
               emit(Resource.Error("Couldn't load data"))
               null
           }
           println("Check Response :$remoteListings")
           remoteListings?.let { listings ->
              db.personProfileDao.clearAllMatches()
               db.personProfileDao.insertProfiles(
                  listings.toEntity()
               )
               emit(Resource.Loading(false))
           }
       }
   }

    @OptIn(ExperimentalPagingApi::class)
    override fun getProfileMatchesPaginated(
        fromNetwork: Boolean
    ): Flow<PagingData<ProfileMatch>> {

        return Pager(
            remoteMediator = MatchMateRemoteMediator(matchMateApi,db),
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { db.personProfileDao.getAllMatches() }
        ).flow
    }

    override suspend fun updateProfileMatchStatus(userId: String, status: Int) {
        db.personProfileDao.updateStatus(userId = userId, status = status)
    }
}