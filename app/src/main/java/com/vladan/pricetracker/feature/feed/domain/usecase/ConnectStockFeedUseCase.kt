package com.vladan.pricetracker.feature.feed.domain.usecase

import com.vladan.pricetracker.feature.feed.domain.repository.FeedRepository
import javax.inject.Inject

class ConnectStockFeedUseCase @Inject constructor(
    private val repository: FeedRepository
) {
    suspend operator fun invoke() {
        repository.connect()
    }
}
