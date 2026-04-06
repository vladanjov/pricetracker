package com.vladan.pricetracker.feature.feed.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vladan.pricetracker.core.designsystem.PriceChangeIndicator
import com.vladan.pricetracker.core.designsystem.PriceGreenFlash
import com.vladan.pricetracker.core.designsystem.PriceRedFlash
import com.vladan.pricetracker.core.domain.model.PriceDirection
import com.vladan.pricetracker.feature.feed.presentation.model.FeedStockUiModel
import kotlinx.coroutines.delay

@Composable
fun StockListItem(
    stock: FeedStockUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var lastSeenPrice by remember { mutableStateOf(stock.price) }
    var flashActive by remember { mutableStateOf(false) }

    LaunchedEffect(stock.price) {
        if (stock.price != lastSeenPrice) {
            if (stock.trend != PriceDirection.NEUTRAL) {
                flashActive = true
                delay(600)
                flashActive = false
            }
            lastSeenPrice = stock.price
        }
    }

    val surfaceColor = MaterialTheme.colorScheme.surfaceContainerLow
    val flashTarget = when (stock.trend) {
        PriceDirection.UP -> PriceGreenFlash
        PriceDirection.DOWN -> PriceRedFlash
        PriceDirection.NEUTRAL -> surfaceColor
    }

    val backgroundColor by animateColorAsState(
        targetValue = if (flashActive) flashTarget else surfaceColor,
        animationSpec = tween(durationMillis = if (flashActive) 100 else 500),
        label = "itemFlash"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stock.symbol,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stock.companyName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = stock.formattedPrice,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                PriceChangeIndicator(
                    trend = stock.trend,
                    changePercent = stock.changePercent
                )
            }
        }
    }
}
