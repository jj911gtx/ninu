package io.pc7.ninu.presentation.login


import io.pc7.ninu.data.network.error.DataError
import io.pc7.ninu.data.network.repository.AuthRepository
import io.pc7.ninu.domain.model.EmptyResult
import io.pc7.ninu.domain.model.ResultMy
import io.pc7.ninu.domain.model.handle
import io.pc7.ninu.domain.model.input.MyInput
import io.pc7.ninu.presentation.util.ViewModelBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VerificationViewModel(
    coroutineScope: CoroutineScope?,
    private val authRepository: AuthRepository,
): ViewModelBase<VerificationState, VerificationAction, VerificationEvent>(
    coroutineScope,
    VerificationState()
) {

    override fun action(action: VerificationAction) {
        when(action){
            is VerificationAction.OnCodeUpdate -> {
                if(action.code.length <= 4){
                    _state.update { it.copy(code = action.code) }
                }
            }
            VerificationAction.Verify -> verify()
            VerificationAction.ResendCode -> resendCode()
        }
    }


    private fun resendCode(){
        viewModelScope.launch {
            authRepository.sendVerificationCode().handle(
                onSuccess = {
                    _state.update { it.copy(
                        resendCodeRespond = null,
                        timeOut = 60,
                    ) }
                    eventChannel.send(VerificationEvent.CodeSentSuccessful)
                },
                onError = { error ->
                    _state.update { it.copy(
                        resendCodeRespond = error
                    ) }
                }
            )
        }
    }

    private fun verify(){
        val code = _state.value.code
        if(isCodeInputOk(code.value)){
            viewModelScope.launch {
                authRepository.checkCode()
            }
        }
    }


    private fun isCodeInputOk(code: String): Boolean{
        if (code.length == 4 && code.all { it.isDigit() }){
            return true
        }else{
            _state.update { it.copy(code = it.code.setErrors(listOf("code format incorrect"))) }
        }
        return false
    }


}

data class VerificationState(
    val email: String = "",
    val code: MyInput<String> = MyInput(""),
    val enableButton: Boolean = false,
    val timeOut: Int? = 60,
    val resendCodeRespond: DataError.Network? = null,
)


sealed class VerificationEvent {
    data object CodeSentSuccessful: VerificationEvent()
}

sealed class VerificationAction {
    data class OnCodeUpdate(val code: String): VerificationAction()
    data object Verify: VerificationAction()
    data object ResendCode: VerificationAction()
}
