package io.pc7.ninu.presentation.register.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.data.network.repository.AuthRepository
import io.pc7.ninu.presentation.register.register.RegistrationViewModel

class RegistrationViewModelAndroid(
    repository: AuthRepository
): ViewModel() {
    val viewModel = RegistrationViewModel(
        coroutineScope = viewModelScope,
        repository = repository
    )


}