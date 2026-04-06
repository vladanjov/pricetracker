package com.vladan.pricetracker.feature.feed.domain.repository

import com.vladan.pricetracker.core.common.ConnectionStatus
import com.vladan.pricetracker.core.domain.model.StockPrice
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun observeStockPrices(): Flow<Map<String, StockPrice>>
    fun observeConnectionStatus(): Flow<ConnectionStatus>
    suspend fun connect()
}
