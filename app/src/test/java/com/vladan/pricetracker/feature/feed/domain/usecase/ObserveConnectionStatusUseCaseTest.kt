package com.vladan.pricetracker.feature.feed.domain.usecase

import app.cash.turbine.test
import com.vladan.pricetracker.core.common.ConnectionStatus
import com.vladan.pricetracker.feature.feed.domain.repository.FeedRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ObserveConnectionStatusUseCaseTest {

    private val repository: FeedRepository = mockk()
    private val useCase = ObserveConnectionStatusUseCase(repository)

    @Test
    fun `invoke returns flow from repository`() = runTest {
        every { repository.observeConnectionStatus() } returns flowOf(
            ConnectionStatus.Connecting,
            ConnectionStatus.Connected
        )

        useCase().test {
            assertEquals(ConnectionStatus.Connecting, awaitItem())
            assertEquals(ConnectionStatus.Connected, awaitItem())
            awaitComplete()
        }

        verify(exactly = 1) { repository.observeConnectionStatus() }
    }

    @Test
    fun `invoke emits error status`() = runTest {
        every { repository.observeConnectionStatus() } returns flowOf(
            ConnectionStatus.Error("timeout")
        )

        useCase().test {
            val status = awaitItem()
            assertEquals(ConnectionStatus.Error("timeout"), status)
            awaitComplete()
        }
    }

    @Test
    fun `invoke emits disconnected status`() = runTest {
        every { repository.observeConnectionStatus() } returns flowOf(
            ConnectionStatus.Disconnected
        )

        useCase().test {
            assertEquals(ConnectionStatus.Disconnected, awaitItem())
            awaitComplete()
        }
    }
}
