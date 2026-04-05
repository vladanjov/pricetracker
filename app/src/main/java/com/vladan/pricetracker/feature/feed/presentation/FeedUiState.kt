package com.vladan.pricetracker.feature.feed.presentation

import androidx.compose.runtime.Immutable
import com.vladan.pricetracker.core.common.ConnectionStatus
import com.vladan.pricetracker.feature.feed.presentation.model.FeedStockUiModel

@Immutable
sealed interface FeedUiState {
    data object Loading : FeedUiState
    data class Success(
        val stocks: List<FeedStockUiModel>,
        val connectionStatus: ConnectionStatus
    ) : FeedUiState
    data class Empty(val connectionStatus: ConnectionStatus) : FeedUiState
}
