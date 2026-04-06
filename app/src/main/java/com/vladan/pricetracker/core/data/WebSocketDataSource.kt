package com.vladan.pricetracker.core.data

import com.vladan.pricetracker.core.common.ConnectionStatus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketDataSource @Inject constructor(
    private val client: OkHttpClient
) {
    private val webSocket = AtomicReference<WebSocket?>(null)

    private val _connectionStatus = MutableStateFlow<ConnectionStatus>(ConnectionStatus.Disconnected)
    val connectionStatus: StateFlow<ConnectionStatus> = _connectionStatus.asStateFlow()

    private val _messages = MutableSharedFlow<String>(extraBufferCapacity = 64)
    val messages: SharedFlow<String> = _messages.asSharedFlow()

    fun connect(url: String) {
        if (_connectionStatus.value is ConnectionStatus.Connected ||
            _connectionStatus.value is ConnectionStatus.Connecting
        ) return

        _connectionStatus.update { ConnectionStatus.Connecting }
        openSocket(url)
    }

    fun send(text: String): Boolean {
        return webSocket.get()?.send(text) ?: false
    }

    fun reconnect(url: String) {
        _connectionStatus.update { ConnectionStatus.Reconnecting }
        webSocket.getAndSet(null)?.close(1000, "Reconnecting")
        openSocket(url)
    }

    private fun openSocket(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        val newSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(ws: WebSocket, response: Response) {
                if (ws !== webSocket.get()) return
                _connectionStatus.update { ConnectionStatus.Connected }
            }

            override fun onMessage(ws: WebSocket, text: String) {
                if (ws !== webSocket.get()) return
                _messages.tryEmit(text)
            }

            override fun onClosing(ws: WebSocket, code: Int, reason: String) {
                ws.close(1000, null)
                if (ws !== webSocket.get()) return
                _connectionStatus.update { ConnectionStatus.Disconnected }
            }

            override fun onClosed(ws: WebSocket, code: Int, reason: String) {
                if (ws !== webSocket.get()) return
                _connectionStatus.update { ConnectionStatus.Disconnected }
            }

            override fun onFailure(ws: WebSocket, t: Throwable, response: Response?) {
                if (ws !== webSocket.get()) return
                _connectionStatus.update { ConnectionStatus.Error(t.message) }
            }
        })
        webSocket.set(newSocket)
    }

    fun disconnect() {
        webSocket.getAndSet(null)?.close(1000, "User stopped")
        _connectionStatus.update { ConnectionStatus.Disconnected }
    }
}
