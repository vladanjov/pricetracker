package com.vladan.pricetracker.feature.details.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vladan.pricetracker.core.domain.model.PriceDirection

@Composable
fun PriceCard(
    price: Double,
    trend: PriceDirection,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
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
