package io.pc7.ninu.presentation.perfumeSelection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.data.network.repository.PerfumeRepository

class WhereToViewModeAndroid(
    perfumeRepository: PerfumeRepository
): ViewModel() {
    val viewModel = WhereToViewModel(
        coroutineScope = viewModelScope,
        perfumeRepository = perfumeRepository
    )
}