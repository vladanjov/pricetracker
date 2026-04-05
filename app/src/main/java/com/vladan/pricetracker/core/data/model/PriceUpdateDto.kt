package com.vladan.pricetracker.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PriceUpdateDto(
    val symbol: String,
    val price: Double
)

@Serializable
data class PriceUpdateMessageDto(
    val updates: List<PriceUpdateDto>
)
