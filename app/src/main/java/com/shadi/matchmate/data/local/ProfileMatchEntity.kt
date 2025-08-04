package com.shadi.matchmate.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "profile_match")
data class ProfileMatchEntity(
    @PrimaryKey
    val userId: String,
    val name: String,
    val profilePicUrl: String,
    val address: String,
    val status: Int = -1,
)

