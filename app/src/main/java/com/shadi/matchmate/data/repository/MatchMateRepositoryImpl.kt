package com.shadi.matchmate.data.repository

import com.shadi.matchmate.data.local.PersonProfileDataBase
import com.shadi.matchmate.data.mapper.toEntity
import com.shadi.matchmate.data.mapper.toProfileMatch


import com.shadi.matchmate.data.remote.MatchMateApi

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
           emit(Resource.Loading(true))
           val localPersonProfileList= db.personProfileDao.getAllMatches()
           emit(
               Resource.Success(
                   data = localPersonProfileList.map {
                       it.toProfileMatch()
                   }
               )
           )

           val isDbEmpty = localPersonProfileList.isEmpty()
           val shouldJustLoadFromCache = !isDbEmpty && !fromNetwork
           if (shouldJustLoadFromCache) {
               emit(Resource.Loading(false))
               return@flow
           }
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
               emit(
                   Resource.Success(
                       data = db.personProfileDao
                           .getAllMatches()
                           .map { it.toProfileMatch() }
                   ))
               emit(Resource.Loading(false))
           }
       }
   }

    override suspend fun updateProfileMatchStatus(userId: String, status: Int) {
        db.personProfileDao.updateStatus(userId = userId, status = status)
    }
}