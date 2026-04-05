package com.vladan.pricetracker.feature.details.presentation

import androidx.compose.runtime.Immutable
import com.vladan.pricetracker.feature.details.presentation.model.DetailStockUiModel

@Immutable
sealed interface DetailsUiState {
    data object Loading : DetailsUiState
    data class Success(val stock: DetailStockUiModel) : DetailsUiState
    data object NotFound : DetailsUiState
}
