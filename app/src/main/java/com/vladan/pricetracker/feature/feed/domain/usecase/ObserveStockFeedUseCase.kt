package com.vladan.pricetracker.feature.feed.domain.usecase

import com.vladan.pricetracker.core.domain.model.StockPrice
import com.vladan.pricetracker.feature.feed.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveStockFeedUseCase @Inject constructor(
    private val repository: FeedRepository
) {
    operator fun invoke(): Flow<Map<String, StockPrice>> = repository.observeStockPrices()
}
