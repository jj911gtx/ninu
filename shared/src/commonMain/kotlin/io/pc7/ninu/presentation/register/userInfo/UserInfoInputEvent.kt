package io.pc7.ninu.presentation.register.userInfo

sealed class UserInfoInputEvent {
    data object Success: UserInfoInputEvent()
}