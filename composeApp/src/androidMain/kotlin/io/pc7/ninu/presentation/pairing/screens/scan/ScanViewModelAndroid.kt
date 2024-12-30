package io.pc7.ninu.presentation.pairing.screens.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.data.network.repository.GeneralRepository
import io.pc7.ninu.presentation.pairing.scan.ScanViewModel

class ScanViewModelAndroid(
    generalRepository: GeneralRepository
): ViewModel() {

    val viewModel = ScanViewModel(
        coroutineScope = viewModelScope,
        repository = generalRepository
    )

}