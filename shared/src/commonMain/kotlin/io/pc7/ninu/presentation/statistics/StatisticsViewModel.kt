package io.pc7.ninu.presentation.statistics


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StatisticsViewModel(
    coroutineScope: CoroutineScope? = null
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(StatisticsState())
    val state: StateFlow<StatisticsState> get() = _state

    private val eventChannel = Channel<StatisticsEvent>()
    val events = eventChannel.receiveAsFlow()

    fun action(action: StatisticsAction) {
//        when(action){
//            
//        }
    }
}

data class StatisticsState(
    val x: Int = 1
)


sealed class StatisticsEvent {

}

sealed class StatisticsAction {

}
