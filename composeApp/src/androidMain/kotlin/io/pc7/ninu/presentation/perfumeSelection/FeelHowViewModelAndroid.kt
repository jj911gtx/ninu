package io.pc7.ninu.presentation.perfumeSelection

import androidx.lifecycle.ViewModel
import io.pc7.ninu.data.network.repository.PerfumeRepository

class FeelHowViewModelAndroid(
    perfumeRepository: PerfumeRepository
): ViewModel() {

    val viewModel = FeelHowViewModel(
        perfumeRepository = perfumeRepository
    )

}