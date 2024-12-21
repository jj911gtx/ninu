package io.pc7.ninu.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class LoginViewModelAndroid(

): ViewModel() {

    val viewModel = LoginViewModel(
        coroutineScope = viewModelScope
    )

}