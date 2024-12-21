package ninu.login.presentation.verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class VerificationViewModelAndroid(

): ViewModel() {

    val viewModel = VerificationViewModel(
        coroutineScope = viewModelScope
    )
}