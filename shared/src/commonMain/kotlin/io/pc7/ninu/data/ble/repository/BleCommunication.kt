package io.pc7.ninu.data.ble.repository

import io.pc7.ninu.data.ble.BLECommunicationHandler
import io.pc7.ninu.data.ble.model.BleConnectionStatus
import io.pc7.ninu.data.ble.model.BleResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BleCommunication(
    private val bleCommunicationHandler: BLECommunicationHandler
) : CoroutineScope {
    protected val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.IO + job



    private val _connected = MutableStateFlow(false)
    val connected = _connected.asStateFlow()


    fun isBluetoothEnabled(): Boolean{
        return bleCommunicationHandler.isBluetoothEnabled()
    }

    init {
        launch {
            bleCommunicationHandler.bleConnection.collect{
                _connected.value = it == BleConnectionStatus.Connected
            }
        }
    }

    /**
     * return if connected*/
    suspend fun connect(macAddress: String = "4F:11:1F:DA:03:28"): BleResult<Unit>{
        return if(connected.value){
            BleResult.Success(Unit)
        }else{
            when(val result = bleCommunicationHandler.connectSuspending(macAddress)){
                is BleResult.Error -> result
                is BleResult.Success -> BleResult.Success(Unit)
            }
        }
    }



    sealed class BleConnectingInfo{
        data class Progress(val message: String): BleConnectingInfo()
        data object Success: BleConnectingInfo()
        data object Error: BleConnectingInfo()
    }



}