package io.pc7.ninu.presentation.home

import io.pc7.ninu.data.ble.repository.BleCommunication
import io.pc7.ninu.presentation.home.HomeScreenAction
import io.pc7.ninu.presentation.home.HomeScreenEvent
import io.pc7.ninu.presentation.home.HomeScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeScreenViewModel(
//    private val bleScanner: BLEScanner,
//    private val bleCommunicationHandler: BleComm
    private val viewModelScope: CoroutineScope,
    private val bleCommunication: BleCommunication
) {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<HomeScreenEvent>()
    val events = eventChannel.receiveAsFlow()

    fun action(action: HomeScreenAction){
        when(action){
            HomeScreenAction.Pair -> pair()
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            bleCommunication.connected.collect { newStatus ->
                _state.update { it.copy(deviceConnected = newStatus) }
                if (newStatus) {
                    eventChannel.send(HomeScreenEvent.BluetoothConnected)
                }
            }
        }
    }

    private fun pair(){
        viewModelScope.launch(Dispatchers.IO) {
//            bleScanner.startDiscovery()
        }

    }





    companion object {
        fun initialize() =
            HomeScreenState(
                devicePercentage = 0.4f,
                deviceConnected = false

            )
    }
}