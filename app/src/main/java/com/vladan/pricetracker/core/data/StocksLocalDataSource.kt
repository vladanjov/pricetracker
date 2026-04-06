package com.vladan.pricetracker.core.data

import com.vladan.pricetracker.core.data.model.PriceUpdateDto
import com.vladan.pricetracker.core.data.model.StockPriceDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface StocksLocalDataSource {
    val stockPrices: StateFlow<Map<String, StockPriceDto>>
    fun observeAll(): StateFlow<Map<String, StockPriceDto>>
    fun observeBySymbol(symbol: String): Flow<StockPriceDto?>
    fun updatePrices(updates: List<PriceUpdateDto>)
}
