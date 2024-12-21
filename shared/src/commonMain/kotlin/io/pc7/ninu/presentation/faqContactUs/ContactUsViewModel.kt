package io.pc7.ninu.presentation.faqContactUs


import io.pc7.ninu.domain.model.input.MyInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ContactUsViewModel(
    coroutineScope: CoroutineScope? = null
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(ContactUsState())
    val state: StateFlow<ContactUsState> get() = _state

    private val eventChannel = Channel<ContactUsEvent>()
    val events = eventChannel.receiveAsFlow()

    fun action(action: ContactUsAction) {
        when(action){
            is ContactUsAction.OnCategoryUpdate -> _state.update { it.copy(category = it.category.update(action.category)) }
            is ContactUsAction.OnCountryUpdate -> _state.update { it.copy(country = it.country.update(action.country)) }
            is ContactUsAction.OnMessageUpdate -> _state.update { it.copy(message = it.message.update(action.message)) }
            is ContactUsAction.OnNameUpdate -> _state.update { it.copy(name = it.name.update(action.name)) }
            is ContactUsAction.OnPhoneUpdate -> _state.update { it.copy(phoneNumber = it.phoneNumber.update(action.phone)) }
            ContactUsAction.OnSend -> send()
        }
    }

    private fun send(){

    }





}

data class ContactUsState(
    val name: MyInput<String> = MyInput(""),
    val phoneNumber: MyInput<String> = MyInput(""),
    val country: MyInput<String> = MyInput(""),
    val category: MyInput<String> = MyInput(""),
    val message: MyInput<String> = MyInput(""),
)


sealed class ContactUsEvent {

}

sealed class ContactUsAction {
    data class OnNameUpdate(val name: String): ContactUsAction()
    data class OnPhoneUpdate(val phone: String): ContactUsAction()
    data class OnCountryUpdate(val country: String): ContactUsAction()
    data class OnCategoryUpdate(val category: String): ContactUsAction()
    data class OnMessageUpdate(val message: String): ContactUsAction()

    data object OnSend: ContactUsAction()
}
