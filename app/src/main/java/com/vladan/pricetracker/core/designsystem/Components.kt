package com.vladan.pricetracker.core.designsystem

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vladan.pricetracker.core.domain.model.PriceDirection
import kotlin.math.abs

@Composable
fun PriceChangeIndicator(
    trend: PriceDirection,
    changePercent: Double,
    modifier: Modifier = Modifier
) {
    val color = when (trend) {
        PriceDirection.UP -> PriceGreen
        PriceDirection.DOWN -> PriceRed
        PriceDirection.NEUTRAL -> PriceGreen
    }
    val arrow = when (trend) {
        PriceDirection.UP -> "\u2191"
        PriceDirection.DOWN -> "\u2193"
        PriceDirection.NEUTRAL -> ""
    }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        if (arrow.isNotEmpty()) {
            Text(
                text = arrow,
                color = color,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(2.dp))
        }
        Text(
            text = String.format("%.2f%%", abs(changePercent)),
            color = color,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
