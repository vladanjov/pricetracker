package com.vladan.pricetracker.core.data.mapper

import com.vladan.pricetracker.core.data.model.StockPriceDto
import com.vladan.pricetracker.core.domain.model.StockPrice

fun StockPriceDto.toDomain(): StockPrice = StockPrice(
    symbol = symbol,
    companyName = companyName,
    price = price,
    previousPrice = previousPrice,
    description = description
)
