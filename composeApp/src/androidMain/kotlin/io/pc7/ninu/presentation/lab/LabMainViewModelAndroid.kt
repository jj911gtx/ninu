package io.pc7.ninu.presentation.lab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.data.ble.repository.PerfumeBleCommunication
import io.pc7.ninu.data.network.repository.PerfumeRepository
import io.pc7.ninu.presentation.lab.screen.LabMainViewModel

class LabMainViewModelAndroid(
    perfumeRepository: PerfumeRepository,
    perfumeCommunication: PerfumeBleCommunication
): ViewModel() {

    val viewModel = LabMainViewModel(
        viewModelScope = viewModelScope,
        perfumeRepository = perfumeRepository,
        perfumeCommunication = perfumeCommunication,
    )
}