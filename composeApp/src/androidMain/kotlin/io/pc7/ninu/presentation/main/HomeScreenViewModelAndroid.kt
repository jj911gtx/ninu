package io.pc7.ninu.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.data.ble.repository.BleCommunication
import io.pc7.ninu.presentation.home.HomeScreenViewModel

class HomeScreenViewModelAndroid(
    bleCommunication: BleCommunication
): ViewModel() {

    val viewModel = HomeScreenViewModel(
        viewModelScope = viewModelScope,
        bleCommunication = bleCommunication,
    )
}