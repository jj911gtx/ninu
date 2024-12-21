package io.pc7.ninu.presentation.register.register

sealed class RegistrationEvent {
    data object Success: RegistrationEvent()
}