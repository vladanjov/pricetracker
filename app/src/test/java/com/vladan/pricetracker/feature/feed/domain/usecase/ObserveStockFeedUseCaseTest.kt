package com.vladan.pricetracker.feature.feed.domain.usecase

import app.cash.turbine.test
import com.vladan.pricetracker.core.domain.model.StockPrice
import com.vladan.pricetracker.feature.feed.domain.repository.FeedRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ObserveStockFeedUseCaseTest {

    private val repository: FeedRepository = mockk()
    private val useCase = ObserveStockFeedUseCase(repository)

    @Test
    fun `invoke returns stock prices flow from repository`() = runTest {
        val stockMap = mapOf(
            "AAPL" to StockPrice("AAPL", "Apple Inc.", 195.0, 189.84, "Apple description")
        )
        every { repository.observeStockPrices() } returns flowOf(stockMap)

        useCase().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals(195.0, result["AAPL"]!!.price, 0.001)
            awaitComplete()
        }

        verify(exactly = 1) { repository.observeStockPrices() }
    }

    @Test
    fun `invoke emits empty map`() = runTest {
        every { repository.observeStockPrices() } returns flowOf(emptyMap())

        useCase().test {
            assertEquals(emptyMap<String, StockPrice>(), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `invoke emits multiple updates`() = runTest {
        val first = mapOf(
            "AAPL" to StockPrice("AAPL", "Apple Inc.", 189.84, 189.84, "desc")
        )
        val second = mapOf(
            "AAPL" to StockPrice("AAPL", "Apple Inc.", 195.0, 189.84, "desc")
        )
        every { repository.observeStockPrices() } returns flowOf(first, second)

        useCase().test {
            assertEquals(189.84, awaitItem()["AAPL"]!!.price, 0.001)
            assertEquals(195.0, awaitItem()["AAPL"]!!.price, 0.001)
            awaitComplete()
        }
    }
}
