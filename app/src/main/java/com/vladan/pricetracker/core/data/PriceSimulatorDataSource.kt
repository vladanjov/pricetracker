package com.vladan.pricetracker.core.data

import com.vladan.pricetracker.core.common.ConnectionStatus
import com.vladan.pricetracker.core.data.model.StockPriceDto
import com.vladan.pricetracker.core.data.model.PriceUpdateDto
import com.vladan.pricetracker.core.data.model.PriceUpdateMessageDto
import kotlinx.coroutines.delay
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class PriceSimulatorDataSource @Inject constructor(
    private val webSocketDataSource: WebSocketDataSource,
    private val stocksLocalDataSource: StocksLocalDataSource,
    private val json: Json
) {

    suspend fun sendPriceUpdates() {
        while (true) {
            delay(2000)
            if (webSocketDataSource.connectionStatus.value !is ConnectionStatus.Connected) continue

            val currentPrices = stocksLocalDataSource.stockPrices.value
            val symbols = currentPrices.keys.toList()
            val updatedSymbols = symbols.shuffled().take(6)

            val updates = updatedSymbols.mapNotNull { symbol ->
                val current = currentPrices[symbol] ?: return@mapNotNull null
                val changePct = (Math.random() * 4.0 - 2.0) / 100.0
                val newPrice = current.price * (1.0 + changePct)
                val rounded = (newPrice * 100.0).roundToInt() / 100.0
                PriceUpdateDto(symbol, rounded)
            }

            val message = json.encodeToString(PriceUpdateMessageDto(updates))
            webSocketDataSource.send(message)
        }
    }
}
