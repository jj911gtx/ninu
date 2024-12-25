package io.pc7.ninu.presentation.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class StatisticsViewModeAndroid(

): ViewModel() {
    val viewModel = StatisticsViewModel(
        coroutineScope = viewModelScope
    )
}