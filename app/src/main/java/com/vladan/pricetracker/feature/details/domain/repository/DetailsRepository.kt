package com.vladan.pricetracker.feature.details.domain.repository

import com.vladan.pricetracker.core.domain.model.StockPrice
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    fun observeStock(symbol: String): Flow<StockPrice?>
}
