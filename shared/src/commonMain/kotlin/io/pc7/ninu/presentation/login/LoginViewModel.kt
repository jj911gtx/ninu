package io.pc7.ninu.presentation.login


import io.pc7.ninu.data.network.error.DataError
import io.pc7.ninu.data.network.repository.AuthRepository
import io.pc7.ninu.data.util.checkEmailPattern
import io.pc7.ninu.domain.model.util.EmptyResult
import io.pc7.ninu.domain.model.util.Resource
import io.pc7.ninu.domain.model.util.ResultMy
import io.pc7.ninu.domain.model.util.handle
import io.pc7.ninu.domain.model.input.MyInput
import io.pc7.ninu.domain.model.input.isInputEmpty
import io.pc7.ninu.presentation.util.ViewModelBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    coroutineScope: CoroutineScope?,
    private val authRepository: AuthRepository,
): ViewModelBase<LoginState, LoginAction,LoginEvent>(
    coroutineScope,
    LoginState()
) {


    override fun action(action: LoginAction) {
        when(action){
            is LoginAction.OnEmailUpdate -> updateEmail(action.email)
            LoginAction.OnEmailRemoveFocus -> _state.update { it.copy(email = it.email.setDisplayErrors()) }
            LoginAction.OnLogin -> login()
            is LoginAction.OnPasswordUpdate -> onUpdatePassword(action.password)
            LoginAction.OnPasswordRemoveFocus -> _state.update { it.copy(password = it.password.setDisplayErrors()) }
            LoginAction.OnRememberChange -> {_state.update { it.copy(rememberMe = !it.rememberMe) }}
            LoginAction.OnFailLoginChange -> updateState { it.copy(failLogin = !it.failLogin) } //TODO remove
        }
    }
    private fun login(){
        val password = _state.value.password.value
        val email = _state.value.email.value
        viewModelScope.launch {
            if(checkAllInputs(email = email, password = password)){
                updateState { it.copy(loginState = Resource.Loading) }
                if(_state.value.failLogin){
                    updateState { it.copy(loginState = Resource.Result(ResultMy.Error(DataError.Network.UNKNOWN))) }
                    return@launch
                }
                //TODO mandatory
                authRepository.login(email, password).handle(
                    onSuccess = {
                        eventChannel.send(LoginEvent.LoginSuccessful)
                    },
                    onError = {
                        updateState { it.copy(loginState = Resource.Result(ResultMy.Error(DataError.Network.UNKNOWN))) }
                    }
                )
            }

        }
    }

    private fun checkAllInputs(email: String, password: String): Boolean{
        var noError = true
        if(emailErrors(email)){
            noError = false
        }
        if(passwordErrors(password)){
            noError = false
        }
        return noError
    }

//region password
    private fun onUpdatePassword(password: String){
        _state.update { it.copy(password = it.password.update(password)) }
        passwordErrors(password)
    }

    private fun passwordErrors(
        password: String
    ): Boolean{
        val errors = mutableListOf<String>()
        if(!password.isInputEmpty(errors)){
            if(password.length < 8){
                errors.add("password must be at least 8 char long")
            }
        }

        _state.update { it.copy(password = it.password.setErrors(errors)) }
        return errors.isNotEmpty()
    }

//endregion


    private fun updateEmail(email: String){
        _state.update { it.copy(email = it.email.update(email)) }
        emailErrors(email)
    }

    private fun emailErrors(email: String): Boolean{
        val errors = mutableListOf<String>()
        if(!email.isInputEmpty(errors)){
            if(!checkEmailPattern(email)){
                errors.add("Invalid email")
            }
        }

        _state.update { it.copy(
            email = it.email.setErrors(errors)
        ) }
        return errors.isNotEmpty()
    }

    init {
        checkAllInputs(
            email = _state.value.email.value,
            password = _state.value.password.value,
        )
    }

}







data class LoginState(
    val email: MyInput<String> = MyInput(""),
    val password: MyInput<String> = MyInput(""),
    val loginState: Resource<EmptyResult<DataError.Network>>? = null,
    val rememberMe: Boolean = false,
    val failLogin: Boolean = false, //TODO remove
)


sealed class LoginEvent {
    data object LoginSuccessful: LoginEvent()
}

sealed class LoginAction {
    data class OnEmailUpdate(val email: String): LoginAction()
    data object OnEmailRemoveFocus: LoginAction()

    data class OnPasswordUpdate(val password: String): LoginAction()
    data object OnPasswordRemoveFocus: LoginAction()

    data object OnRememberChange: LoginAction()
    data object OnFailLoginChange: LoginAction()

    data object OnLogin: LoginAction()
}
