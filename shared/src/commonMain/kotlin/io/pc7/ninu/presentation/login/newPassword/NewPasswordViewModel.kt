package ninu.login.presentation.newPassword


import core.presentation.model.input.MyInput
import core.presentation.model.input.isInputEmpty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewPasswordViewModel(
    coroutineScope: CoroutineScope? = null
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(NewPasswordState())
    val state: StateFlow<NewPasswordState> get() = _state

    private val eventChannel = Channel<NewPasswordEvent>()
    val events = eventChannel.receiveAsFlow()

    fun action(action: NewPasswordAction) {
        when(action){
            NewPasswordAction.Confirm -> confirmUpdate()
            NewPasswordAction.OnConfirmNewPasswordRemoveFocus -> _state.update { it.copy(confirmPassword = it.confirmPassword.setDisplayErrors()) }
            is NewPasswordAction.OnConfirmNewPasswordUpdate -> updateConfirmPassword(action.confirmPassword)
            NewPasswordAction.OnNewPasswordRemoveFocus -> _state.update { it.copy(password = it.password.setDisplayErrors()) }
            is NewPasswordAction.OnNewPasswordUpdate -> updatePassword(action.password)
        }
        checkEnableButton()
    }


    private fun confirmUpdate(){
        displayAllErrors()
        val password = _state.value.password.value
        val confirmPassword = _state.value.confirmPassword.value
        if(checkAllPasswords(password, confirmPassword)){
            viewModelScope.launch {

            }
        }
    }




    private fun updatePassword(password: String){
        _state.update { it.copy(password = it.password.update(password)) }
        passwordErrors(password)
    }
    private fun passwordErrors(password: String): Boolean{
        val errors = mutableListOf<String>()
        if(!password.isInputEmpty(errors)){
            if(password.length < 8){
                errors.add("password must be at least 8 char long")
            }
        }

        _state.update { it.copy(password = it.password.setErrors(errors)) }
        return errors.isEmpty()
    }

    private fun updateConfirmPassword(confirmPassword: String){
        _state.update { it.copy(confirmPassword = it.confirmPassword.update(confirmPassword)) }
        confirmPasswordErrors(confirmPassword = _state.value.password.value, password = confirmPassword)
    }
    private fun confirmPasswordErrors(password: String, confirmPassword: String): Boolean{
        val errors = mutableListOf<String>()
        if(!password.isInputEmpty(errors)){
            if(password != confirmPassword){
                errors.add("passwords must match")
            }
        }

        _state.update { it.copy(confirmPassword = it.confirmPassword.setErrors(errors)) }
        return errors.isEmpty()
    }

    private fun checkEnableButton(){
        _state.update {
            it.copy(buttonEnabled =
                _state.value.password.errors.isEmpty()
                    && _state.value.confirmPassword.errors.isEmpty()
                    && _state.value.confirmPassword.value == _state.value.password.value
            )
        }
    }


    private fun displayAllErrors(){
        _state.update {
            it.copy(
                password = it.password.setDisplayErrors(),
                confirmPassword = it.confirmPassword.setDisplayErrors(),
            )
        }
    }


    private fun checkAllPasswords(
        password: String,
        confirmPassword: String
    ): Boolean{
        var noError = true
        if(passwordErrors(password)){
            noError = false
        }
        if(confirmPasswordErrors(password = password, confirmPassword =confirmPassword)){
            noError = false
        }
        return noError
    }



    init {
        checkAllPasswords(
            password = _state.value.password.value,
            confirmPassword = _state.value.confirmPassword.value,
        )
    }

}

data class NewPasswordState(
    val password: MyInput<String> = MyInput(""),
    val confirmPassword: MyInput<String> = MyInput(""),
    val buttonEnabled: Boolean = false
)


sealed class NewPasswordEvent {

}

sealed class NewPasswordAction {
    data class OnNewPasswordUpdate(val password: String): NewPasswordAction()
    data object OnNewPasswordRemoveFocus: NewPasswordAction()
    data class OnConfirmNewPasswordUpdate(val confirmPassword: String): NewPasswordAction()
    data object OnConfirmNewPasswordRemoveFocus: NewPasswordAction()
    data object Confirm: NewPasswordAction()
}
