package com.vladan.pricetracker.core.data

import app.cash.turbine.test
import com.vladan.pricetracker.core.data.model.PriceUpdateDto
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class StocksLocalDataSourceImplTest {

    private lateinit var dataSource: StocksLocalDataSourceImpl

    @Before
    fun setup() {
        dataSource = StocksLocalDataSourceImpl(StockSymbolsDataSource())
    }

    @Test
    fun `initial state contains all stock symbols`() {
        val prices = dataSource.stockPrices.value

        assertEquals(25, prices.size)
        assert(prices.containsKey("AAPL"))
        assert(prices.containsKey("MSFT"))
        assert(prices.containsKey("GOOGL"))
    }

    @Test
    fun `initial prices equal base prices`() {
        val aapl = dataSource.stockPrices.value["AAPL"]!!

        assertEquals(189.84, aapl.price, 0.001)
        assertEquals(189.84, aapl.previousPrice, 0.001)
        assertEquals("Apple Inc.", aapl.companyName)
    }

    @Test
    fun `observeAll returns same state flow as stockPrices`() {
        val all = dataSource.observeAll().value

        assertEquals(dataSource.stockPrices.value, all)
    }

    @Test
    fun `observeBySymbol emits matching stock`() = runTest {
        dataSource.observeBySymbol("AAPL").test {
            val item = awaitItem()
            assertEquals("AAPL", item?.symbol)
            assertEquals(189.84, item!!.price, 0.001)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `observeBySymbol emits null for unknown symbol`() = runTest {
        dataSource.observeBySymbol("UNKNOWN").test {
            assertNull(awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `updatePrices updates price and sets previousPrice`() {
        val updates = listOf(PriceUpdateDto(symbol = "AAPL", price = 200.0))

        dataSource.updatePrices(updates)

        val aapl = dataSource.stockPrices.value["AAPL"]!!
        assertEquals(200.0, aapl.price, 0.001)
        assertEquals(189.84, aapl.previousPrice, 0.001)
    }

    @Test
    fun `updatePrices handles multiple updates`() {
        val updates = listOf(
            PriceUpdateDto(symbol = "AAPL", price = 200.0),
            PriceUpdateDto(symbol = "MSFT", price = 430.0)
        )

        dataSource.updatePrices(updates)

        assertEquals(200.0, dataSource.stockPrices.value["AAPL"]!!.price, 0.001)
        assertEquals(430.0, dataSource.stockPrices.value["MSFT"]!!.price, 0.001)
    }

    @Test
    fun `updatePrices ignores unknown symbols`() {
        val sizeBefore = dataSource.stockPrices.value.size
        val updates = listOf(PriceUpdateDto(symbol = "UNKNOWN", price = 100.0))

        dataSource.updatePrices(updates)

        assertEquals(sizeBefore, dataSource.stockPrices.value.size)
        assertNull(dataSource.stockPrices.value["UNKNOWN"])
    }

    @Test
    fun `updatePrices preserves companyName and description`() {
        val updates = listOf(PriceUpdateDto(symbol = "AAPL", price = 200.0))

        dataSource.updatePrices(updates)

        val aapl = dataSource.stockPrices.value["AAPL"]!!
        assertEquals("Apple Inc.", aapl.companyName)
        assert(aapl.description.isNotEmpty())
    }

    @Test
    fun `consecutive updates chain previousPrice correctly`() {
        dataSource.updatePrices(listOf(PriceUpdateDto(symbol = "AAPL", price = 200.0)))
        dataSource.updatePrices(listOf(PriceUpdateDto(symbol = "AAPL", price = 210.0)))

        val aapl = dataSource.stockPrices.value["AAPL"]!!
        assertEquals(210.0, aapl.price, 0.001)
        assertEquals(200.0, aapl.previousPrice, 0.001)
    }

    @Test
    fun `observeBySymbol emits updated value after updatePrices`() = runTest {
        dataSource.observeBySymbol("AAPL").test {
            awaitItem()

            dataSource.updatePrices(listOf(PriceUpdateDto(symbol = "AAPL", price = 200.0)))

            val updated = awaitItem()
            assertEquals(200.0, updated!!.price, 0.001)
            cancelAndConsumeRemainingEvents()
        }
    }
}
