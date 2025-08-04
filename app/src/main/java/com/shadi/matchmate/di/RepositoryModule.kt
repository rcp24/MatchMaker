package com.shadi.matchmate.di

import com.shadi.matchmate.data.repository.MatchMateRepositoryImpl
import com.shadi.matchmate.domain.repository.MatchMateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMatchMateRepository(
        matchMateRepositoryImpl: MatchMateRepositoryImpl
    ): MatchMateRepository
}