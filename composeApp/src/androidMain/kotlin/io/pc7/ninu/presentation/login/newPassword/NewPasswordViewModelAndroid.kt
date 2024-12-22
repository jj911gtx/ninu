package io.pc7.ninu.presentation.login.newPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.data.network.repository.AuthRepository
import io.pc7.ninu.presentation.login.NewPasswordViewModel

class NewPasswordViewModelAndroid(
    authRepository: AuthRepository
): ViewModel() {
    val viewModel = NewPasswordViewModel(
        coroutineScope = viewModelScope,
        authRepository = authRepository
    )
}