package com.vladan.pricetracker.di

import com.vladan.pricetracker.core.data.StocksLocalDataSource
import com.vladan.pricetracker.core.data.StocksLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindStocksLocalDataSource(impl: StocksLocalDataSourceImpl): StocksLocalDataSource
}
