package io.pc7.ninu.presentation.util

import io.pc7.ninu.data.util.flow.CommonStateFlow
import io.pc7.ninu.data.util.flow.common
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class ViewModelBase<State, Action, Event,>(
    coroutineScope: CoroutineScope?,
    state: State
) {
    protected val _state: MutableStateFlow<State> = MutableStateFlow(state)
    val state: CommonStateFlow<State> get() = _state.asStateFlow().common()

    protected val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    val viewModelScope: CoroutineScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    fun destroy() {
        viewModelScope.cancel()
    }

    open fun action(action: Action) {}



}
