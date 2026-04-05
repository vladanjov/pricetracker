package com.vladan.pricetracker.feature.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladan.pricetracker.feature.details.domain.usecase.ObserveStockDetailUseCase
import com.vladan.pricetracker.feature.details.presentation.mapper.toDetailUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observeStockDetail: ObserveStockDetailUseCase
) : ViewModel() {

    private val symbol: String = checkNotNull(savedStateHandle["symbol"])

    val uiState: StateFlow<DetailsUiState> = observeStockDetail(symbol)
        .map { stock ->
            if (stock != null) DetailsUiState.Success(stock.toDetailUiModel())
            else DetailsUiState.NotFound
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DetailsUiState.Loading
        )
}
