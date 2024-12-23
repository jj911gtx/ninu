package io.pc7.ninu.data.ble

import io.pc7.ninu.data.ble.model.BleConnectionStatus
import io.pc7.ninu.data.ble.model.BleResult
import kotlinx.coroutines.flow.StateFlow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


expect class BLECommunicationHandler {
    val bleConnection: StateFlow<BleConnectionStatus>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun readCharacteristic(serviceUuid: Uuid, characteristicUUID: Uuid): BleResult<ByteArray>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun writeCharacteristic(serviceUuid: Uuid, characteristicUUID: Uuid, value: ByteArray): BleResult<Unit>

    suspend fun connectSuspending(): BleResult<Unit>
}

