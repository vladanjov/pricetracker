package com.vladan.pricetracker.feature.feed.presentation.mapper

import com.vladan.pricetracker.core.domain.model.StockPrice
import com.vladan.pricetracker.feature.feed.presentation.model.FeedStockUiModel

fun StockPrice.toFeedUiModel(): FeedStockUiModel = FeedStockUiModel(
    symbol = symbol,
    companyName = companyName,
    formattedPrice = String.format("$%.2f", price),
    trend = direction,
    changePercent = changePercent,
    price = price
)
