package com.vladan.pricetracker.feature.feed.domain.usecase

import com.vladan.pricetracker.feature.feed.domain.repository.FeedRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ConnectStockFeedUseCaseTest {

    private val repository: FeedRepository = mockk()
    private val useCase = ConnectStockFeedUseCase(repository)

    @Test
    fun `invoke calls repository connect`() = runTest {
        coEvery { repository.connect() } returns Unit

        useCase()

        coVerify(exactly = 1) { repository.connect() }
    }

    @Test(expected = RuntimeException::class)
    fun `invoke propagates exception from repository`() = runTest {
        coEvery { repository.connect() } throws RuntimeException("Connection failed")

        useCase()
    }
}
