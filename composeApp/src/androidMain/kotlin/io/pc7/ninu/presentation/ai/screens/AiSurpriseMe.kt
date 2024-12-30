package io.pc7.ninu.presentation.ai.screens


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.presentation.ai.AiSurpriseMeViewModel

class AiSurpriseMeViewModelAndroid(

) : ViewModel() {
    val viewModel = AiSurpriseMeViewModel(
        coroutineScope = viewModelScope,
    )
}

