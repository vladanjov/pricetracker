package com.vladan.pricetracker.feature.feed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladan.pricetracker.core.common.ConnectionStatus
import com.vladan.pricetracker.feature.feed.domain.usecase.ObserveConnectionStatusUseCase
import com.vladan.pricetracker.feature.feed.domain.usecase.ObserveStockFeedUseCase
import com.vladan.pricetracker.feature.feed.presentation.mapper.toFeedUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface FeedUiEvent {
    data class ShowSnackbar(val message: String) : FeedUiEvent
}

@HiltViewModel
class FeedViewModel @Inject constructor(
    observeStockFeed: ObserveStockFeedUseCase,
    observeConnectionStatus: ObserveConnectionStatusUseCase
) : ViewModel() {

    private val _uiEvent = Channel<FeedUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            observeConnectionStatus()
                .distinctUntilChanged()
                .collect { status ->
                    if (status is ConnectionStatus.Error) {
                        val message = status.message ?: "Connection failed"
                        _uiEvent.send(FeedUiEvent.ShowSnackbar(message))
                    }
                }
        }
    }

    val uiState: StateFlow<FeedUiState> = combine(
        observeStockFeed(),
        observeConnectionStatus()
    ) { prices, status ->
        if (prices.isEmpty()) {
            FeedUiState.Empty(connectionStatus = status)
        } else {
            FeedUiState.Success(
                stocks = prices.values
                    .sortedByDescending { it.price }
                    .map { it.toFeedUiModel() },
                connectionStatus = status
            )
        }
    }.flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FeedUiState.Loading
        )
}
