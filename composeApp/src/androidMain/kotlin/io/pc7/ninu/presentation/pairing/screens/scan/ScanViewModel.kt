package io.pc7.ninu.presentation.pairing.screens.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.presentation.pairing.scan.ScanViewModel

class ScanViewModelAndroid(

): ViewModel() {

    val viewModel = ScanViewModel(coroutineScope = viewModelScope)

}