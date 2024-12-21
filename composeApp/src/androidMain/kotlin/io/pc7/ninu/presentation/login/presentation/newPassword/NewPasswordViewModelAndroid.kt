package ninu.login.presentation.newPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class NewPasswordViewModelAndroid(

): ViewModel() {
    val viewModel = NewPasswordViewModel(
        coroutineScope = viewModelScope
    )
}