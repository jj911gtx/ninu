package io.pc7.ninu.data.ble.repository

import io.pc7.ninu.data.ble.BLECommunicationHandler
import io.pc7.ninu.data.ble.model.BleResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

abstract class BleCommunicationBase(
    private val bleCommunicationHandler: BLECommunicationHandler
): CoroutineScope {

    protected val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.IO + job


    @OptIn(ExperimentalUuidApi::class)
    protected abstract val serviceUUID: Uuid

    @OptIn(ExperimentalUuidApi::class)
    protected suspend fun readCharacteristic(uuidString: String): BleResult<ByteArray> {
        return bleCommunicationHandler.readCharacteristic(serviceUUID, Uuid.parse(uuidString))
    }
    @OptIn(ExperimentalUuidApi::class)
    suspend fun writeCharacteristic(uuidString: String, value: ByteArray): BleResult<Unit> {
        return bleCommunicationHandler.writeCharacteristic(serviceUUID, Uuid.parse(uuidString), value)
    }
}