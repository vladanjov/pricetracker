package com.vladan.pricetracker.feature.details.data.repository

import com.vladan.pricetracker.core.data.StocksLocalDataSource
import com.vladan.pricetracker.core.data.mapper.toDomain
import com.vladan.pricetracker.core.domain.model.StockPrice
import com.vladan.pricetracker.feature.details.domain.repository.DetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailsRepositoryImpl @Inject constructor(
    private val stocksLocalDataSource: StocksLocalDataSource
) : DetailsRepository {

    override fun observeStock(symbol: String): Flow<StockPrice?> {
        return stocksLocalDataSource.observeBySymbol(symbol).map { it?.toDomain() }
    }
}
