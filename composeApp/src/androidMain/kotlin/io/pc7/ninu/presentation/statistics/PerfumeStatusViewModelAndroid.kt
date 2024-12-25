package io.pc7.ninu.presentation.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class PerfumeStatusViewModelAndroid(

): ViewModel() {

    val viewModel = PerfumeStatusViewModel(
        viewModelScope
    )

}