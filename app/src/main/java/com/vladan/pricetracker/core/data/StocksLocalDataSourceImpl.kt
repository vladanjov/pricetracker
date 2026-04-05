package com.vladan.pricetracker.core.data

import com.vladan.pricetracker.core.data.model.PriceUpdateDto
import com.vladan.pricetracker.core.data.model.StockPriceDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StocksLocalDataSourceImpl @Inject constructor(
    stockSymbolsDataSource: StockSymbolsDataSource
) : StocksLocalDataSource {
    private val _stockPrices = MutableStateFlow(
        stockSymbolsDataSource.all.associate { info ->
            info.symbol to StockPriceDto(
                symbol = info.symbol,
                companyName = info.companyName,
                price = info.basePrice,
                previousPrice = info.basePrice,
                description = info.description
            )
        }
    )

    override val stockPrices: StateFlow<Map<String, StockPriceDto>> = _stockPrices.asStateFlow()

    override fun observeAll(): StateFlow<Map<String, StockPriceDto>> = stockPrices

    override fun observeBySymbol(symbol: String): Flow<StockPriceDto?> {
        return _stockPrices.map { prices -> prices[symbol] }
    }

    override fun updatePrices(updates: List<PriceUpdateDto>) {
        _stockPrices.update { current ->
            val mutable = current.toMutableMap()
            for (update in updates) {
                val existing = mutable[update.symbol] ?: continue
                mutable[update.symbol] = existing.copy(
                    previousPrice = existing.price,
                    price = update.price
                )
            }
            mutable
        }
    }
}
