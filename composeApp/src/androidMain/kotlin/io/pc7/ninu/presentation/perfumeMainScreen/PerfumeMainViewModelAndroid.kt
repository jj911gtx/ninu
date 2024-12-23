package io.pc7.ninu.presentation.perfumeMainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.data.ble.model.PerfumeSN
import io.pc7.ninu.data.network.repository.PerfumeRepository
import io.pc7.ninu.domain.model.perfume.Fragrance
import io.pc7.ninu.presentation.perfumeMain.PerfumeMainViewModel


class PerfumeMainViewModelAndroid(
    perfumeRepository: PerfumeRepository,
    intensity: Int?,
    fragrances: Array<Fragrance>
): ViewModel() {


    val viewModel: PerfumeMainViewModel = PerfumeMainViewModel(
        viewModelScope,
        perfumeRepository = perfumeRepository,
        inIntensity = intensity,
        fragrances = fragrances
    )

}