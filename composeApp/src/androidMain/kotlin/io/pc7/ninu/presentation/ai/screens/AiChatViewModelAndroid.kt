package io.pc7.ninu.presentation.ai.screens


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.presentation.ai.AiChatViewModel

class AiChatViewModelAndroid(

) : ViewModel() {
    val viewModel = AiChatViewModel(
        coroutineScope = viewModelScope,
    )
}

