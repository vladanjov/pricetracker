package com.vladan.pricetracker.feature.details.presentation.model

import com.vladan.pricetracker.core.domain.model.PriceDirection

data class DetailStockUiModel(
    val symbol: String,
    val companyName: String,
    val formattedPrice: String,
    val formattedPreviousPrice: String,
    val formattedChangePercent: String,
    val description: String,
    val trend: PriceDirection,
    val price: Double,
    val changePercent: Double
)
