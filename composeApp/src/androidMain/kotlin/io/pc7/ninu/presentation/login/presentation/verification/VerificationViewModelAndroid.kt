package ninu.login.presentation.verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.presentation.login.VerificationViewModel

class VerificationViewModelAndroid(

): ViewModel() {

    val viewModel = VerificationViewModel(
        coroutineScope = viewModelScope
    )
}