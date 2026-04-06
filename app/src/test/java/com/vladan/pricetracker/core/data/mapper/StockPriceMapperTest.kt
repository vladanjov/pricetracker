package com.vladan.pricetracker.core.data.mapper

import com.vladan.pricetracker.core.data.model.StockPriceDto
import org.junit.Assert.assertEquals
import org.junit.Test

class StockPriceMapperTest {

    @Test
    fun `toDomain maps all fields correctly`() {
        val dto = StockPriceDto(
            symbol = "AAPL",
            companyName = "Apple Inc.",
            price = 195.50,
            previousPrice = 189.84,
            description = "Apple description"
        )

        val result = dto.toDomain()

        assertEquals("AAPL", result.symbol)
        assertEquals("Apple Inc.", result.companyName)
        assertEquals(195.50, result.price, 0.001)
        assertEquals(189.84, result.previousPrice, 0.001)
        assertEquals("Apple description", result.description)
    }

    @Test
    fun `toDomain preserves equal price and previousPrice`() {
        val dto = StockPriceDto(
            symbol = "MSFT",
            companyName = "Microsoft Corp.",
            price = 420.55,
            previousPrice = 420.55,
            description = "Microsoft description"
        )

        val result = dto.toDomain()

        assertEquals(result.price, result.previousPrice, 0.001)
    }
}
