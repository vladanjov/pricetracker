package com.vladan.pricetracker.feature.feed.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vladan.pricetracker.R
import com.vladan.pricetracker.core.common.ConnectionStatus
import com.vladan.pricetracker.feature.feed.presentation.components.ConnectionStatusIndicator
import com.vladan.pricetracker.feature.feed.presentation.components.StockListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    onStockClick: (String) -> Unit,
    isRunning: Boolean,
    onToggleRunning: () -> Unit,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is FeedUiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    val status = when (val state = uiState) {
                        is FeedUiState.Success -> state.connectionStatus
                        is FeedUiState.Empty -> state.connectionStatus
                        is FeedUiState.Loading -> ConnectionStatus.Connecting
                    }
                    ConnectionStatusIndicator(status = status)
                },
                actions = {
                    if (isRunning) {
                        TextButton(onClick = onToggleRunning) {
                            Text(stringResource(R.string.stop), color = MaterialTheme.colorScheme.error)
                        }
                    } else {
                        TextButton(onClick = onToggleRunning) {
                            Text(stringResource(R.string.start), color = com.vladan.pricetracker.core.designsystem.PriceGreen)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        when (val state = uiState) {
            is FeedUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is FeedUiState.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_stocks_available),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            is FeedUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(
                        items = state.stocks,
                        key = { it.symbol }
                    ) { stock ->
                        StockListItem(
                            stock = stock,
                            onClick = { onStockClick(stock.symbol) }
                        )
                    }
                }
            }
        }
    }
}
