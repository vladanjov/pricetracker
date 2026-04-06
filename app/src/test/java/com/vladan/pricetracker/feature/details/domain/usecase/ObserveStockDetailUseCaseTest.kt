package com.vladan.pricetracker.feature.details.domain.usecase

import app.cash.turbine.test
import com.vladan.pricetracker.core.domain.model.StockPrice
import com.vladan.pricetracker.feature.details.domain.repository.DetailsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ObserveStockDetailUseCaseTest {

    private val repository: DetailsRepository = mockk()
    private val useCase = ObserveStockDetailUseCase(repository)

    @Test
    fun `invoke returns stock detail flow from repository`() = runTest {
        val stock = StockPrice("AAPL", "Apple Inc.", 195.0, 189.84, "Apple description")
        every { repository.observeStock("AAPL") } returns flowOf(stock)

        useCase("AAPL").test {
            val result = awaitItem()
            assertEquals("AAPL", result?.symbol)
            assertEquals(195.0, result!!.price, 0.001)
            awaitComplete()
        }

        verify(exactly = 1) { repository.observeStock("AAPL") }
    }

    @Test
    fun `invoke emits null for unknown symbol`() = runTest {
        every { repository.observeStock("UNKNOWN") } returns flowOf(null)

        useCase("UNKNOWN").test {
            assertNull(awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `invoke passes symbol to repository`() = runTest {
        every { repository.observeStock("MSFT") } returns flowOf(
            StockPrice("MSFT", "Microsoft Corp.", 420.55, 420.55, "desc")
        )

        useCase("MSFT").test {
            assertEquals("MSFT", awaitItem()?.symbol)
            awaitComplete()
        }

        verify(exactly = 1) { repository.observeStock("MSFT") }
    }

    @Test
    fun `invoke emits updated values`() = runTest {
        val first = StockPrice("AAPL", "Apple Inc.", 189.84, 189.84, "desc")
        val second = StockPrice("AAPL", "Apple Inc.", 200.0, 189.84, "desc")
        every { repository.observeStock("AAPL") } returns flowOf(first, second)

        useCase("AAPL").test {
            assertEquals(189.84, awaitItem()!!.price, 0.001)
            assertEquals(200.0, awaitItem()!!.price, 0.001)
            awaitComplete()
        }
    }
}
