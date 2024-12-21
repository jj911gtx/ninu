package io.pc7.ninu.presentation.settings.profile


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

class ProfileViewModel(
    coroutineScope: CoroutineScope? = null
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> get() = _state

    private val eventChannel = Channel<ProfileEvent>()
    val events = eventChannel.receiveAsFlow()

    fun action(action: ProfileAction) {
//        when(action){
//            
//        }
    }
}

data class ProfileState(
    val name: String = "Janez",
    val email: String = "",
    val username: String = "",
    val dateOfBirth: LocalDate = LocalDate(1990, 2,10)

)


sealed class ProfileEvent {

}

sealed class ProfileAction {

}
