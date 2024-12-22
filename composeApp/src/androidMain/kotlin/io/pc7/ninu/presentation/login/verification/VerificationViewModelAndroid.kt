package io.pc7.ninu.presentation.login.verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.data.network.repository.AuthRepository
import io.pc7.ninu.presentation.login.VerificationViewModel

class VerificationViewModelAndroid(
    email: String,
    authRepository: AuthRepository
): ViewModel() {

    val viewModel = VerificationViewModel(
        coroutineScope = viewModelScope,
        email = email,
        authRepository = authRepository
    )
}