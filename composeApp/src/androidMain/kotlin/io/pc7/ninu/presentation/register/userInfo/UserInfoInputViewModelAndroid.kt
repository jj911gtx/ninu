package io.pc7.ninu.presentation.register.userInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.data.network.repository.AuthRepository

class UserInfoInputViewModelAndroid(
    repository: AuthRepository
):ViewModel() {

    val viewModel = UserInfoInputViewModel(
        coroutineScope = viewModelScope,
        repository = repository
    )
}