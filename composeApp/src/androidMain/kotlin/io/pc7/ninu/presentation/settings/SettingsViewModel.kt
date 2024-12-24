package ninu.other.settings.main


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel(
    coroutineScope: CoroutineScope? = null,
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> get() = _state

    private val eventChannel = Channel<SettingsEvent>()
    val events = eventChannel.receiveAsFlow()

    fun action(action: SettingsAction) {
//        when(action){
//            
//        }
    }
}

data class SettingsState(
    val x: Int = 1,
)


sealed class SettingsEvent {
    data object LoggedOut: SettingsEvent()
}

sealed class SettingsAction {
    data object LogOut: SettingsAction()
}
