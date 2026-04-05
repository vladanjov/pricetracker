package com.vladan.pricetracker.feature.details.domain.usecase

import com.vladan.pricetracker.feature.details.domain.repository.DetailsRepository
import com.vladan.pricetracker.core.domain.model.StockPrice
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveStockDetailUseCase @Inject constructor(
    private val repository: DetailsRepository
) {
    operator fun invoke(symbol: String): Flow<StockPrice?> {
        return repository.observeStock(symbol)
    }
}
