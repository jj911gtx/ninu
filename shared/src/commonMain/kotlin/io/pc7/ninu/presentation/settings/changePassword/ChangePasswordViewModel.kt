package io.pc7.ninu.presentation.settings.changePassword


import io.pc7.ninu.data.util.checkNewPasswordErrors
import io.pc7.ninu.domain.model.input.MyInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ChangePasswordViewModel(
    coroutineScope: CoroutineScope? = null
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(ChangePasswordState())
    val state: StateFlow<ChangePasswordState> get() = _state

    private val eventChannel = Channel<ChangePasswordEvent>()
    val events = eventChannel.receiveAsFlow()

    fun action(action: ChangePasswordAction) {
        when(action){
            is ChangePasswordAction.OnConfirmNewPasswordUpdate -> {
                _state.update { it.copy(currentPassword = it.currentPassword.update(action.value)) }
                confirmNewPasswordErrors()
            }
            is ChangePasswordAction.OnConfirmNewPasswordUnfocus -> _state.update { it.copy(currentPassword = it.currentPassword.setDisplayErrors()) }
            is ChangePasswordAction.OnCurrentPasswordUpdate -> {
                _state.update { it.copy(newPassword = it.newPassword.update(action.value)) }
                currentPasswordErrors()
            }
            is ChangePasswordAction.OnCurrentPasswordUnfocus -> _state.update { it.copy(newPassword = it.newPassword.setDisplayErrors()) }
            is ChangePasswordAction.OnNewPasswordUpdate -> {
                _state.update { it.copy(confirmNewPassword = it.confirmNewPassword.update(action.value)) }
                newPasswordError()
            }
            is ChangePasswordAction.OnNewPasswordUnfocus -> _state.update { it.copy(confirmNewPassword = it.confirmNewPassword.setDisplayErrors()) }

            ChangePasswordAction.ConfirmChange -> updatePassword()

        }
    }


    private fun updatePassword(){
        displayErrorsOnAllInputs()
    }




    private fun newPasswordError(){
        val errors = _state.value.currentPassword.value.checkNewPasswordErrors()
        _state.update { it.copy(currentPassword = it.currentPassword.setErrors(errors)) }
    }

    private fun confirmNewPasswordErrors(){
        val newPassword = _state.value.newPassword.value
        val confirmPassword = _state.value.confirmNewPassword.value
        val errors = mutableListOf<String>()
        if(newPassword.isEmpty()){
            errors.add("Required")
        }else{
            if(newPassword == confirmPassword){
                errors.add("Passwords doesnt match")
            }
        }
        _state.update { it.copy(confirmNewPassword = it.confirmNewPassword.setErrors(errors)) }
    }


    private fun currentPasswordErrors(){
        val password = _state.value.currentPassword.value
        val errors = mutableListOf<String>()
        if(password.isEmpty()){
            errors.add("Required")
        }else{
            if(password.length < 8){
                errors.add("password must be at lest 8 char long")
            }
        }
        _state.update { it.copy(currentPassword = it.currentPassword.setErrors(errors)) }
    }


    private fun newPasswordErrors(){

    }




    private fun displayErrorsOnAllInputs(){
        _state.update { it.copy(
            confirmNewPassword = it.confirmNewPassword.setDisplayErrors(),
            newPassword = it.newPassword.setDisplayErrors(),
            currentPassword = it.currentPassword.setDisplayErrors(),
        )}
    }



    init {
        currentPasswordErrors()
        newPasswordErrors()
        confirmNewPasswordErrors()
    }


}

data class ChangePasswordState(
    val currentPassword: MyInput<String> = MyInput(""),
    val newPassword: MyInput<String>  = MyInput(""),
    val confirmNewPassword: MyInput<String>  = MyInput(""),
    val buttonEnabled: Boolean = false
)


sealed class ChangePasswordEvent {


}

sealed class ChangePasswordAction {
    data class OnCurrentPasswordUpdate(val value: String): ChangePasswordAction()
    data object OnCurrentPasswordUnfocus: ChangePasswordAction()
    data class OnNewPasswordUpdate(val value: String): ChangePasswordAction()
    data object OnNewPasswordUnfocus: ChangePasswordAction()
    data class OnConfirmNewPasswordUpdate(val value: String): ChangePasswordAction()
    data object OnConfirmNewPasswordUnfocus: ChangePasswordAction()
    data object ConfirmChange: ChangePasswordAction()
}
