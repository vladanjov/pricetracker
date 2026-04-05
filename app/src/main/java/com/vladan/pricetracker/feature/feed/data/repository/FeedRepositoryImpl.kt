package com.vladan.pricetracker.feature.feed.data.repository

import com.vladan.pricetracker.core.common.ConnectionStatus
import com.vladan.pricetracker.core.data.PriceSimulatorDataSource
import com.vladan.pricetracker.core.data.PriceUpdateParser
import com.vladan.pricetracker.core.data.StocksLocalDataSource
import com.vladan.pricetracker.core.data.WebSocketDataSource
import com.vladan.pricetracker.core.data.mapper.toDomain
import com.vladan.pricetracker.core.domain.model.StockPrice
import com.vladan.pricetracker.feature.feed.domain.repository.FeedRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedRepositoryImpl @Inject constructor(
    private val webSocketDataSource: WebSocketDataSource,
    private val stocksLocalDataSource: StocksLocalDataSource,
    private val priceSimulatorDataSource: PriceSimulatorDataSource,
    private val priceUpdateParser: PriceUpdateParser
) : FeedRepository {

    override fun observeStockPrices(): Flow<Map<String, StockPrice>> =
        stocksLocalDataSource.observeAll().map { map ->
            map.mapValues { (_, entity) -> entity.toDomain() }
        }

    override fun observeConnectionStatus(): Flow<ConnectionStatus> = webSocketDataSource.connectionStatus

    override suspend fun connect() {
        var retryCount = 0

        while (retryCount < MAX_RETRIES) {
            if (retryCount == 0) {
                webSocketDataSource.connect(WS_URL)
            } else {
                webSocketDataSource.reconnect(WS_URL)
            }

            try {
                coroutineScope {
                    launch {
                        webSocketDataSource.messages.collect { text ->
                            val updates = priceUpdateParser.parse(text)
                            stocksLocalDataSource.updatePrices(updates)
                        }
                    }
                    launch {
                        priceSimulatorDataSource.sendPriceUpdates()
                    }
                    launch {
                        webSocketDataSource.connectionStatus.first { status ->
                            status is ConnectionStatus.Error || status is ConnectionStatus.Disconnected
                        }
                        throw ReconnectException()
                    }
                }
            } catch (_: ReconnectException) {
                retryCount++
            } finally {
                webSocketDataSource.disconnect()
            }
        }

        throw MaxRetriesExceededException()
    }

    private class ReconnectException : Exception()
    class MaxRetriesExceededException : Exception("Failed to connect after $MAX_RETRIES attempts")

    companion object {
        private const val WS_URL = "wss://ws.postman-echo.com/raw"
        private const val MAX_RETRIES = 3
    }
}
