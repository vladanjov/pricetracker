package com.vladan.pricetracker.feature.feed.presentation.model

import com.vladan.pricetracker.core.domain.model.PriceDirection

data class FeedStockUiModel(
    val symbol: String,
    val companyName: String,
    val formattedPrice: String,
    val trend: PriceDirection,
    val changePercent: Double,
    val price: Double
)
