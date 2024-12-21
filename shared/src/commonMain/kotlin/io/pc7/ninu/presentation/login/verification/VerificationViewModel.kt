package ninu.login.presentation.verification


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VerificationViewModel(
    coroutineScope: CoroutineScope? = null
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(VerificationState())
    val state: StateFlow<VerificationState> get() = _state

    private val eventChannel = Channel<VerificationEvent>()
    val events = eventChannel.receiveAsFlow()

    fun action(action: VerificationAction) {
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

        }
    }

    private fun verify(){
        val code = _state.value.code
        if(isCodeInputOk(code)){
            viewModelScope.launch {

            }
        }
    }


    private fun isCodeInputOk(code: String): Boolean{
        return code.length == 4 && code.all { it.isDigit() }
    }


}

data class VerificationState(
    val email: String = "",
    val code: String = "",
    val enableButton: Boolean = false,
    val timeOut: Int? = 60
)


sealed class VerificationEvent {

}

sealed class VerificationAction {
    data class OnCodeUpdate(val code: String): VerificationAction()
    data object Verify: VerificationAction()
    data object ResendCode: VerificationAction()
}
