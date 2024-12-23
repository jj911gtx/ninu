package ninu.other.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.presentation.home.HomeScreenViewModel

class HomeScreenViewModelAndroid(

): ViewModel() {

    val viewModel = HomeScreenViewModel(
        viewModelScope = viewModelScope
    )
}