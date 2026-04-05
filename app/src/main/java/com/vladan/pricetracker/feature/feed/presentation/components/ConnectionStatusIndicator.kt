package com.vladan.pricetracker.feature.feed.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vladan.pricetracker.core.common.ConnectionStatus

@Composable
fun ConnectionStatusIndicator(status: ConnectionStatus, modifier: Modifier = Modifier) {
    val (dot, label) = when (status) {
        is ConnectionStatus.Connected -> "\uD83D\uDFE2" to "Connected"
        is ConnectionStatus.Disconnected -> "\uD83D\uDD34" to "Disconnected"
        is ConnectionStatus.Connecting -> "\uD83D\uDFE1" to "Connecting..."
        is ConnectionStatus.Reconnecting -> "\uD83D\uDFE1" to "Reconnecting..."
        is ConnectionStatus.Error -> "\uD83D\uDD34" to "Error"
    }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = dot, fontSize = 12.sp)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, fontSize = 13.sp)
    }
}
