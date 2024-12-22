package io.pc7.ninu.presentation.login


import io.pc7.ninu.data.network.error.DataError
import io.pc7.ninu.data.network.repository.AuthRepository
import io.pc7.ninu.domain.model.EmptyResult
import io.pc7.ninu.domain.model.Resource
import io.pc7.ninu.domain.model.ResultMy
import io.pc7.ninu.domain.model.handle
import io.pc7.ninu.domain.model.input.MyInput
import io.pc7.ninu.presentation.util.ViewModelBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VerificationViewModel(
    coroutineScope: CoroutineScope?,
    email: String,
    private val authRepository: AuthRepository,
): ViewModelBase<VerificationState, VerificationAction, VerificationEvent>(
    coroutineScope,
    VerificationState(email = email)
) {

    init {
        resendCode()
    }

    override fun action(action: VerificationAction) {
        when(action){
            is VerificationAction.OnCodeUpdate -> {
                if(action.code.length <= 4 && action.code.all { it.isDigit() }){
                    updateState { it.copy(code = action.code) }
                    if(action.code.length == 4){
                        verify()
                    }
                }
            }
            VerificationAction.Verify -> verify()
            VerificationAction.ResendCode -> resendCode()
        }
    }


    private fun resendCode(){
        viewModelScope.launch {
            _state.update { it.copy(resendCodeRespond = Resource.Loading) }
            authRepository.sendVerificationCode(_state.value.email).handle(
                onSuccess = {
                    _state.update { it.copy(
                        resendCodeRespond = null,
                    ) }
                    startTimeout()
                },
                onError = { error ->
                    _state.update { it.copy(
                        resendCodeRespond = Resource.Result(error)
                    ) }
                }
            )
        }
    }

    private fun verify(){
        val code = _state.value.code
        if(isCodeInputOk(code)){
            viewModelScope.launch(Dispatchers.IO) {
                _state.update { it.copy(checkCode = Resource.Loading) }
                authRepository.checkCode(code).handle(
                    onSuccess = {
                        updateState { it.copy(
                            checkCode = null,
                            code = ""
                        ) }
                        eventChannel.send(VerificationEvent.VerificationSuccessful)
                    },
                    onError = { error ->
                        _state.update { it.copy(
                            checkCode = Resource.Result(ResultMy.Error(error)),
                            code = ""
                        ) }
                    }
                )
            }
        }
    }


    private fun isCodeInputOk(code: String): Boolean{
        return code.length == 4 && code.all { it.isDigit() }
    }


    private var timeoutCoroutineScope: Job? = null
    private fun startTimeout(){
        timeoutCoroutineScope?.cancel()
        timeoutCoroutineScope = viewModelScope.launch(Dispatchers.IO) {
            for (i in 10 downTo 0) {
                updateState { it.copy(timeOut = i) }
                delay(1000)
            }
            updateState { it.copy(timeOut = null) }
            timeoutCoroutineScope?.cancel()
        }
    }


}

data class VerificationState(
    val email: String = "",
    val code: String = "",
    val enableButton: Boolean = false,
    val timeOut: Int? = 10,
    val resendCodeRespond: Resource<DataError.Network>? = null,
    val checkCode: Resource<ResultMy<Boolean, DataError.Network>>? = null,
)


sealed class VerificationEvent {
    data object VerificationSuccessful: VerificationEvent()
}

sealed class VerificationAction {
    data class OnCodeUpdate(val code: String): VerificationAction()
    data object Verify: VerificationAction()
    data object ResendCode: VerificationAction()
}
