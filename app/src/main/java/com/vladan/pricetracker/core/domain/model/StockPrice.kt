package com.vladan.pricetracker.core.domain.model

data class StockPrice(
    val symbol: String,
    val companyName: String,
    val price: Double,
    val previousPrice: Double,
    val description: String
) {
    val direction: PriceDirection
        get() = when {
            price > previousPrice -> PriceDirection.UP
            price < previousPrice -> PriceDirection.DOWN
            else -> PriceDirection.NEUTRAL
        }

    val changePercent: Double
        get() = if (previousPrice != 0.0) {
            ((price - previousPrice) / previousPrice) * 100.0
        } else {
            0.0
        }
}
