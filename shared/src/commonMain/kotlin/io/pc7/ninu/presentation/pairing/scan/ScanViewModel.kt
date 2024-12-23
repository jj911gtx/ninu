package io.pc7.ninu.presentation.pairing.scan


import io.pc7.ninu.presentation.pairing.scan.ScanAction
import io.pc7.ninu.presentation.pairing.scan.ScanEvent
import io.pc7.ninu.presentation.pairing.scan.ScanState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScanViewModel(
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(ScanState())
    val state: StateFlow<ScanState> get() = _state

    private val eventChannel = Channel<ScanEvent>()
    val events = eventChannel.receiveAsFlow()

    fun action(action: ScanAction) {
        when (action) {
            is ScanAction.OnSerialNumberUpdate -> _state.update { it.copy(serialNumber = it.serialNumber.update(action.sn)) }
            ScanAction.OnProceed -> checkSerialnumber()
        }
    }


    private fun checkSerialnumber(){
        viewModelScope.launch {
            if(true){
                eventChannel.send(ScanEvent.NavNext)
            }
        }
    }

}