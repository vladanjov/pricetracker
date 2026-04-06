package com.vladan.pricetracker.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Feed : Route

    @Serializable
    data class Details(val symbol: String) : Route
}
