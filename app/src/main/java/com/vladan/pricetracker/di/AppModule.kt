package com.vladan.pricetracker.di

import com.vladan.pricetracker.feature.details.data.repository.DetailsRepositoryImpl
import com.vladan.pricetracker.feature.details.domain.repository.DetailsRepository
import com.vladan.pricetracker.feature.feed.data.repository.FeedRepositoryImpl
import com.vladan.pricetracker.feature.feed.domain.repository.FeedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindFeedRepository(impl: FeedRepositoryImpl): FeedRepository

    @Binds
    @Singleton
    abstract fun bindDetailsRepository(impl: DetailsRepositoryImpl): DetailsRepository
}
