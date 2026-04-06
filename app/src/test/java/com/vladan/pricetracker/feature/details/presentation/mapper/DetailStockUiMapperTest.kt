package com.vladan.pricetracker.feature.details.presentation.mapper

import com.vladan.pricetracker.core.domain.model.PriceDirection
import com.vladan.pricetracker.core.domain.model.StockPrice
import org.junit.Assert.assertEquals
import org.junit.Test

class DetailStockUiMapperTest {

    @Test
    fun `toDetailUiModel maps symbol and companyName`() {
        val stock = stockPrice(price = 100.0, previousPrice = 100.0)

        val result = stock.toDetailUiModel()

        assertEquals("AAPL", result.symbol)
        assertEquals("Apple Inc.", result.companyName)
    }

    @Test
    fun `toDetailUiModel formats price with dollar sign and two decimals`() {
        val stock = stockPrice(price = 189.8, previousPrice = 150.0)

        val result = stock.toDetailUiModel()

        assertEquals("$189.80", result.formattedPrice)
    }

    @Test
    fun `toDetailUiModel formats previousPrice with dollar sign and two decimals`() {
        val stock = stockPrice(price = 200.0, previousPrice = 189.8)

        val result = stock.toDetailUiModel()

        assertEquals("$189.80", result.formattedPreviousPrice)
    }

    @Test
    fun `toDetailUiModel formats changePercent with two decimals and percent sign`() {
        val stock = stockPrice(price = 210.0, previousPrice = 200.0)

        val result = stock.toDetailUiModel()

        assertEquals("5.00%", result.formattedChangePercent)
    }

    @Test
    fun `toDetailUiModel maps description`() {
        val stock = stockPrice(price = 100.0, previousPrice = 100.0)

        val result = stock.toDetailUiModel()

        assertEquals("Apple description", result.description)
    }

    @Test
    fun `toDetailUiModel sets UP trend when price increased`() {
        val stock = stockPrice(price = 200.0, previousPrice = 180.0)

        val result = stock.toDetailUiModel()

        assertEquals(PriceDirection.UP, result.trend)
    }

    @Test
    fun `toDetailUiModel sets DOWN trend when price decreased`() {
        val stock = stockPrice(price = 170.0, previousPrice = 180.0)

        val result = stock.toDetailUiModel()

        assertEquals(PriceDirection.DOWN, result.trend)
    }

    @Test
    fun `toDetailUiModel sets NEUTRAL trend when price unchanged`() {
        val stock = stockPrice(price = 180.0, previousPrice = 180.0)

        val result = stock.toDetailUiModel()

        assertEquals(PriceDirection.NEUTRAL, result.trend)
    }

    @Test
    fun `toDetailUiModel passes raw price and changePercent`() {
        val stock = stockPrice(price = 210.0, previousPrice = 200.0)

        val result = stock.toDetailUiModel()

        assertEquals(210.0, result.price, 0.001)
        assertEquals(5.0, result.changePercent, 0.001)
    }

    @Test
    fun `toDetailUiModel handles negative change`() {
        val stock = stockPrice(price = 190.0, previousPrice = 200.0)

        val result = stock.toDetailUiModel()

        assertEquals("-5.00%", result.formattedChangePercent)
        assertEquals(PriceDirection.DOWN, result.trend)
    }

    private fun stockPrice(price: Double, previousPrice: Double) = StockPrice(
        symbol = "AAPL",
        companyName = "Apple Inc.",
        price = price,
        previousPrice = previousPrice,
        description = "Apple description"
    )
}
