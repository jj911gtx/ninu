package io.pc7.ninu.presentation.perfumeDetails


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PerfumeDetailsViewModel(
    coroutineScope: CoroutineScope? = null,
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(PerfumeDetailsState())
    val state: StateFlow<PerfumeDetailsState> get() = _state

    private val eventChannel = Channel<PerfumeDetailsEvent>()
    val events = eventChannel.receiveAsFlow()

    fun action(action: PerfumeDetailsAction) {
//        when(action){
//            
//        }
    }
}

data class PerfumeDetailsState(
    val x: Int = 1,
)


sealed class PerfumeDetailsEvent {

}

sealed class PerfumeDetailsAction {

}
