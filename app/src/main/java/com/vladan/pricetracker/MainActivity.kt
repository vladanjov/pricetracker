package com.vladan.pricetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.vladan.pricetracker.core.designsystem.PriceTrackerTheme
import com.vladan.pricetracker.core.navigation.PriceTrackerNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PriceTrackerTheme {
                val navController = rememberNavController()
                val isRunning by mainViewModel.isRunning.collectAsStateWithLifecycle()
                PriceTrackerNavHost(
                    navController = navController,
                    isRunning = isRunning,
                    onToggleRunning = mainViewModel::toggleRunning
                )
            }
        }
    }
}
