package io.pc7.ninu.data.ble.repository

import io.pc7.ninu.data.ble.BLECommunicationHandler
import io.pc7.ninu.data.ble.model.BleConnectionStatus
import io.pc7.ninu.data.ble.model.BleResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.CoroutineContext

abstract class BleCommunication(
    private val bleCommunicationHandler: BLECommunicationHandler
) : CoroutineScope {
    protected val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.IO + job



    suspend fun isConnected(): Boolean{
        return bleCommunicationHandler.bleConnection.value == BleConnectionStatus.Connected
    }

    /**
     * return if connected*/
    suspend fun connect() = flow<BleConnectingInfo>{
        if(isConnected()){
            emit(BleConnectingInfo.Success)
        }else{
            when(bleCommunicationHandler.connectSuspending()){
                is BleResult.Error -> emit(BleConnectingInfo.Error)
                is BleResult.Success -> emit(BleConnectingInfo.Success)
            }
        }
    }



    sealed class BleConnectingInfo{
        data class Progress(val message: String): BleConnectingInfo()
        data object Success: BleConnectingInfo()
        data object Error: BleConnectingInfo()
    }



}