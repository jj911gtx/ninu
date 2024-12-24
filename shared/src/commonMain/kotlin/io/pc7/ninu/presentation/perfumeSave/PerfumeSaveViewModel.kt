package io.pc7.ninu.presentation.perfumeSave


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PerfumeSaveViewModel(
    coroutineScope: CoroutineScope? = null,
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(PerfumeSaveState())
    val state: StateFlow<PerfumeSaveState> get() = _state

    private val eventChannel = Channel<PerfumeSaveEvent>()
    val events = eventChannel.receiveAsFlow()

    fun action(action: PerfumeSaveAction) {
//        when(action){
//            
//        }
    }
}

data class PerfumeSaveState(
    val x: Int = 1,
)


sealed class PerfumeSaveEvent {

}

sealed class PerfumeSaveAction {

}
