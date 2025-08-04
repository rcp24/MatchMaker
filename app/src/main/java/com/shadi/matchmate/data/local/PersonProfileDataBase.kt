package com.shadi.matchmate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    exportSchema = true, version = 1,
    entities = [ProfileMatchEntity::class]
)
abstract class PersonProfileDataBase :RoomDatabase(){
    abstract val personProfileDao :PersonProfileDao
}