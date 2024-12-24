package io.pc7.ninu.presentation.perfumeDetailsGeneral


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PerfumeDetailsGeneralViewModel(
    coroutineScope: CoroutineScope? = null,
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(PerfumeDetailsGeneralState())
    val state: StateFlow<PerfumeDetailsGeneralState> get() = _state

    private val eventChannel = Channel<PerfumeDetailsGeneralEvent>()
    val events = eventChannel.receiveAsFlow()

    fun action(action: PerfumeDetailsGeneralAction) {
//        when(action){
//            
//        }
    }
}

data class PerfumeDetailsGeneralState(
    val icon: String = "",
//    val greatFor
)


sealed class PerfumeDetailsGeneralEvent {

}

sealed class PerfumeDetailsGeneralAction {

}
