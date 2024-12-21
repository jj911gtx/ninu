package ninu.login.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ninu.other.loginRegister.login.presentation.login.LoginViewModel

class LoginViewModelAndroid(

): ViewModel() {

    val viewModel = LoginViewModel(
        coroutineScope = viewModelScope
    )

}