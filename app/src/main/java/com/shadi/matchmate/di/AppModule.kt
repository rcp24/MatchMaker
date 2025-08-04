package com.shadi.matchmate.di

import android.app.Application
import androidx.room.Room
import com.shadi.matchmate.data.local.PersonProfileDataBase
import com.shadi.matchmate.data.remote.MatchMateApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    @Singleton
    fun provideMatchMateApi(): MatchMateApi {
        return Retrofit.Builder()
            .baseUrl(MatchMateApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }).build())
            .build()
            .create()
    }
    @Provides
    @Singleton
    fun provideMatchMateDatabase(app:Application): PersonProfileDataBase {
        return Room.databaseBuilder(
            app,
            PersonProfileDataBase::class.java,
            "person_profile.db"
        )
            .build()
    }
}