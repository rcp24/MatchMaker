package com.shadi.matchmate.data.remote

import com.shadi.matchmate.data.remote.dto.PersonProfileDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchMateApi {

    @GET("api/")
    suspend fun getAllProfileMatches(@Query("results") count: Int): PersonProfileDto

    companion object {
        const val BASE_URL = "https://randomuser.me/"
    }
}