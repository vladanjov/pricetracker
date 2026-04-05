package com.vladan.pricetracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladan.pricetracker.feature.feed.domain.usecase.ConnectStockFeedUseCase
import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val connectStockFeed: ConnectStockFeedUseCase
) : ViewModel() {

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    private var feedJob: Job? = null

    fun toggleRunning() {
        if (_isRunning.value) stop() else start()
    }

    private fun start() {
        if (feedJob?.isActive == true) return
        _isRunning.update { true }
        feedJob = viewModelScope.launch {
            try {
                connectStockFeed()
            } catch (e: Exception) {
                Log.e(TAG, "Stock feed failed", e)
            } finally {
                _isRunning.update { false }
            }
        }
    }

    private fun stop() {
        feedJob?.cancel()
        feedJob = null
        _isRunning.update { false }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
