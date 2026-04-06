package com.vladan.pricetracker.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.vladan.pricetracker.feature.details.presentation.DetailsScreen
import com.vladan.pricetracker.feature.feed.presentation.FeedScreen

@Composable
fun PriceTrackerNavHost(
    navController: NavHostController,
    isRunning: Boolean,
    onToggleRunning: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Route.Feed
    ) {
        composable<Route.Feed> {
            FeedScreen(
                onStockClick = { symbol ->
                    navController.navigate(Route.Details(symbol))
                },
                isRunning = isRunning,
                onToggleRunning = onToggleRunning
            )
        }

        composable<Route.Details>(
            deepLinks = listOf(
                navDeepLink<Route.Details>(basePath = "stocks://symbol")
            )
        ) {
            DetailsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
