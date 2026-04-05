package com.vladan.pricetracker.feature.feed.domain.usecase

import com.vladan.pricetracker.core.common.ConnectionStatus
import com.vladan.pricetracker.feature.feed.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveConnectionStatusUseCase @Inject constructor(
    private val repository: FeedRepository
) {
    operator fun invoke(): Flow<ConnectionStatus> = repository.observeConnectionStatus()
}
