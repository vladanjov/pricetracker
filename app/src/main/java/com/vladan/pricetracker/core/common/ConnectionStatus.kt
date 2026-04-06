package com.vladan.pricetracker.core.common

sealed interface ConnectionStatus {
    data object Connected : ConnectionStatus
    data object Disconnected : ConnectionStatus
    data object Connecting : ConnectionStatus
    data object Reconnecting : ConnectionStatus
    data class Error(val message: String? = null) : ConnectionStatus
}
