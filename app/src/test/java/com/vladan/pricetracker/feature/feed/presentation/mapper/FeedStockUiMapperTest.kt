package com.vladan.pricetracker.feature.feed.presentation.mapper

import com.vladan.pricetracker.core.domain.model.PriceDirection
import com.vladan.pricetracker.core.domain.model.StockPrice
import org.junit.Assert.assertEquals
import org.junit.Test

class FeedStockUiMapperTest {

    @Test
    fun `toFeedUiModel maps symbol and companyName`() {
        val stock = stockPrice(price = 100.0, previousPrice = 100.0)

        val result = stock.toFeedUiModel()

        assertEquals("AAPL", result.symbol)
        assertEquals("Apple Inc.", result.companyName)
    }

    @Test
    fun `toFeedUiModel formats price with dollar sign and two decimals`() {
        val stock = stockPrice(price = 189.8, previousPrice = 189.8)

        val result = stock.toFeedUiModel()

        assertEquals("$189.80", result.formattedPrice)
    }

    @Test
    fun `toFeedUiModel sets UP trend when price increased`() {
        val stock = stockPrice(price = 200.0, previousPrice = 180.0)

        val result = stock.toFeedUiModel()

        assertEquals(PriceDirection.UP, result.trend)
    }

    @Test
    fun `toFeedUiModel sets DOWN trend when price decreased`() {
        val stock = stockPrice(price = 170.0, previousPrice = 180.0)

        val result = stock.toFeedUiModel()

        assertEquals(PriceDirection.DOWN, result.trend)
    }

    @Test
    fun `toFeedUiModel sets NEUTRAL trend when price unchanged`() {
        val stock = stockPrice(price = 180.0, previousPrice = 180.0)

        val result = stock.toFeedUiModel()

        assertEquals(PriceDirection.NEUTRAL, result.trend)
    }

    @Test
    fun `toFeedUiModel calculates changePercent correctly`() {
        val stock = stockPrice(price = 210.0, previousPrice = 200.0)

        val result = stock.toFeedUiModel()

        assertEquals(5.0, result.changePercent, 0.001)
    }

    @Test
    fun `toFeedUiModel passes raw price`() {
        val stock = stockPrice(price = 123.456, previousPrice = 100.0)

        val result = stock.toFeedUiModel()

        assertEquals(123.456, result.price, 0.001)
    }

    private fun stockPrice(price: Double, previousPrice: Double) = StockPrice(
        symbol = "AAPL",
        companyName = "Apple Inc.",
        price = price,
        previousPrice = previousPrice,
        description = "Apple description"
    )
}
