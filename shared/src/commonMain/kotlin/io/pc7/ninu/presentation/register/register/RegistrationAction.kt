package io.pc7.ninu.presentation.register.register

sealed class RegistrationAction {
    data class OnNameUpdate(val name: String): RegistrationAction()
    data object OnNameRemoveFocus: RegistrationAction()

    data class OnSurnameUpdate(val surname: String): RegistrationAction()
    data object OnSurnameRemoveFocus: RegistrationAction()

    data class OnEmailUpdate(val email: String): RegistrationAction()
    data object OnEmailRemoveFocus: RegistrationAction()

    data class OnPasswordUpdate(val password: String): RegistrationAction()
    data object OnPasswordRemoveFocus: RegistrationAction()

    data object OnSignup: RegistrationAction()
}