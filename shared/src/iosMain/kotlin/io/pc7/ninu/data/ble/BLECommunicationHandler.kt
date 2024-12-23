package io.pc7.ninu.data.ble

import io.pc7.ninu.data.ble.model.BleConnectionStatus
import io.pc7.ninu.data.ble.model.BleResult
import kotlinx.coroutines.flow.StateFlow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

actual class BLECommunicationHandler {

    actual val bleConnection: StateFlow<BleConnectionStatus>
        get() = TODO("Not yet implemented")

    @OptIn(ExperimentalUuidApi::class)
    actual suspend fun readCharacteristic(
        serviceUuid: Uuid,
        characteristicUUID: Uuid,
    ): BleResult<ByteArray> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    actual suspend fun writeCharacteristic(
        serviceUuid: Uuid,
        characteristicUUID: Uuid,
        value: ByteArray,
    ): BleResult<Unit> {
        TODO("Not yet implemented")
    }

    actual suspend fun connectSuspending(): BleResult<Unit> {
        TODO("Not yet implemented")
    }
}