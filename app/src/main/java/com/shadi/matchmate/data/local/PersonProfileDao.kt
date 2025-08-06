package com.shadi.matchmate.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shadi.matchmate.domain.model.ProfileMatch
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfiles(profileList: List<ProfileMatchEntity>)

    @Query("SELECT * FROM profile_match")
     fun getAllMatches(): PagingSource<Int, ProfileMatch>

    @Query("SELECT COUNT(*) FROM profile_match")
    suspend fun getCount(): Int

    @Query("SELECT * FROM profile_match WHERE userId = :userId")
    fun getProfileById(userId: String): ProfileMatchEntity

    @Query("UPDATE profile_match SET status = :status WHERE userId = :userId")
    suspend fun updateStatus(
        userId: String,
        status: Int,
    )
    @Query("DELETE FROM profile_match")
    suspend fun clearAllMatches()
}