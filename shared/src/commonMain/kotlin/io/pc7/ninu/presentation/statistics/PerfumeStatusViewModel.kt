package io.pc7.ninu.presentation.statistics


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PerfumeStatusViewModel(
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(PerfumeStatusState())
    val state: StateFlow<PerfumeStatusState> get() = _state

    private val eventChannel = Channel<PerfumeStatusEvent>()
    val events = eventChannel.receiveAsFlow()

    fun action(action: PerfumeStatusAction) {
//        when(action){
//            
//        }
    }
}

data class PerfumeStatusState(
    val x: Int = 1
)


sealed class PerfumeStatusEvent {

}

sealed class PerfumeStatusAction {

}
