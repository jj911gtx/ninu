package io.pc7.ninu.presentation.settings.changePasword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.presentation.settings.changePassword.ChangePasswordViewModel

class ChangePasswordViewModelAndroid(

): ViewModel() {

    val viewModel = ChangePasswordViewModel(
        coroutineScope = viewModelScope
    )
}