package com.vladan.pricetracker.core.data

import com.vladan.pricetracker.core.data.model.PriceUpdateDto
import com.vladan.pricetracker.core.data.model.PriceUpdateMessageDto
import kotlinx.serialization.json.Json
import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PriceUpdateParser @Inject constructor(
    private val json: Json
) {

    fun parse(text: String): List<PriceUpdateDto> {
        return try {
            json.decodeFromString<PriceUpdateMessageDto>(text).updates
        } catch (e: Exception) {
            Log.w("PriceUpdateParser", "Failed to parse message: ${e.message}")
            emptyList()
        }
    }
}
