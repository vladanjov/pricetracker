package com.vladan.pricetracker.feature.details.presentation.mapper

import com.vladan.pricetracker.core.domain.model.StockPrice
import com.vladan.pricetracker.feature.details.presentation.model.DetailStockUiModel

fun StockPrice.toDetailUiModel(): DetailStockUiModel = DetailStockUiModel(
    symbol = symbol,
    companyName = companyName,
    formattedPrice = String.format("$%.2f", price),
    formattedPreviousPrice = String.format("$%.2f", previousPrice),
    formattedChangePercent = String.format("%.2f%%", changePercent),
    description = description,
    trend = direction,
    price = price,
    changePercent = changePercent
)
