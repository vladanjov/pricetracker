package com.vladan.pricetracker.feature.details.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vladan.pricetracker.core.designsystem.PriceGreen
import com.vladan.pricetracker.core.designsystem.PriceRed
import com.vladan.pricetracker.core.domain.model.PriceDirection
import kotlinx.coroutines.delay

@Composable
fun PriceCard(
    price: Double,
    trend: PriceDirection,
    modifier: Modifier = Modifier
) {
    var flashActive by remember { mutableStateOf(false) }
    val flashTarget = when (trend) {
        PriceDirection.UP -> PriceGreen.copy(alpha = 0.15f)
        PriceDirection.DOWN -> PriceRed.copy(alpha = 0.15f)
        PriceDirection.NEUTRAL -> Color.Transparent
    }

    LaunchedEffect(price) {
        if (trend != PriceDirection.NEUTRAL) {
            flashActive = true
            delay(600)
            flashActive = false
        }
    }

    val backgroundColor by animateColorAsState(
        targetValue = if (flashActive) flashTarget else Color.Transparent,
        animationSpec = tween(durationMillis = if (flashActive) 100 else 500),
        label = "priceFlash"
    )

    Box(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .padding(horizontal = 32.dp, vertical = 16.dp)
    ) {
        Text(
            text = String.format("$%.2f", price),
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp
        )
    }
}
