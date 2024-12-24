package io.pc7.ninu.presentation.perfumeDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.data.network.model.navigation.NavigatePerfumeMain
import io.pc7.ninu.data.network.repository.PerfumeRepository
import io.pc7.ninu.presentation.perfumeDetailsGeneral.PerfumeMainViewModel


class PerfumeMainViewModelAndroid(
    perfumeRepository: PerfumeRepository,
    navigatePerfumeMain: NavigatePerfumeMain
): ViewModel() {


    val viewModel: PerfumeMainViewModel = PerfumeMainViewModel(
        viewModelScope,
        perfumeRepository = perfumeRepository,
        navigatePerfumeMain = navigatePerfumeMain
    )

}