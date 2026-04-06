package com.vladan.pricetracker.core.data

import io.mockk.mockkStatic
import io.mockk.every
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PriceUpdateParserTest {

    private lateinit var parser: PriceUpdateParser

    @Before
    fun setup() {
        mockkStatic(android.util.Log::class)
        every { android.util.Log.w(any<String>(), any<String>()) } returns 0

        parser = PriceUpdateParser(Json { ignoreUnknownKeys = true })
    }

    @Test
    fun `parse returns updates from valid JSON`() {
        val json = """{"updates":[{"symbol":"AAPL","price":195.50},{"symbol":"MSFT","price":425.00}]}"""

        val result = parser.parse(json)

        assertEquals(2, result.size)
        assertEquals("AAPL", result[0].symbol)
        assertEquals(195.50, result[0].price, 0.001)
        assertEquals("MSFT", result[1].symbol)
        assertEquals(425.00, result[1].price, 0.001)
    }

    @Test
    fun `parse returns empty list for single update in JSON`() {
        val json = """{"updates":[{"symbol":"NVDA","price":140.00}]}"""

        val result = parser.parse(json)

        assertEquals(1, result.size)
        assertEquals("NVDA", result[0].symbol)
        assertEquals(140.00, result[0].price, 0.001)
    }

    @Test
    fun `parse returns empty list for empty updates array`() {
        val json = """{"updates":[]}"""

        val result = parser.parse(json)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `parse returns empty list for invalid JSON`() {
        val result = parser.parse("not valid json")

        assertTrue(result.isEmpty())
    }

    @Test
    fun `parse returns empty list for malformed structure`() {
        val json = """{"data":[{"symbol":"AAPL","price":195.50}]}"""

        val result = parser.parse(json)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `parse returns empty list for empty string`() {
        val result = parser.parse("")

        assertTrue(result.isEmpty())
    }
}
