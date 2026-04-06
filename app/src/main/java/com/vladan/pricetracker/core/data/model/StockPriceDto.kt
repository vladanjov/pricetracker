package com.vladan.pricetracker.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class StockPriceDto(
    val symbol: String,
    val companyName: String,
    val price: Double,
    val previousPrice: Double,
    val description: String
)
