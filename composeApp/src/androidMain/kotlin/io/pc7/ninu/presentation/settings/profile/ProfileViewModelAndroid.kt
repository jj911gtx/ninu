package io.pc7.ninu.presentation.settings.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.presentation.settings.profile.ProfileViewModel

class ProfileViewModelAndroid(

): ViewModel() {

    val viewModel = ProfileViewModel(
        coroutineScope = viewModelScope
    )
}