package io.pc7.ninu.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.data.network.repository.AuthRepository

class LoginViewModelAndroid(
    authRepository: AuthRepository
): ViewModel() {

    val viewModel = LoginViewModel(
        coroutineScope = viewModelScope,
        authRepository = authRepository
    )

}