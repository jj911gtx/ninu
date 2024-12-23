package io.pc7.ninu.data.ble.repository

import io.pc7.ninu.data.ble.mapper.int
import io.pc7.ninu.data.ble.mapper.toByteArray
import core.bluetooth.services.SystemService
import core.bluetooth.services.SystemService.Companion.CHARACTERISTIC_ACTIVATION_UUID
import core.bluetooth.services.SystemService.Companion.CHARACTERISTIC_SYSTEM_LOCKED_UUID
import core.bluetooth.services.SystemService.Companion.CHARACTERISTIC_TIME_SYNC_UUID
import io.pc7.ninu.data.ble.BLECommunicationHandler
import io.pc7.ninu.data.ble.model.BleResult
import io.pc7.ninu.data.ble.whenHandle
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class SystemCommunication(
    bleCommunicationHandler: BLECommunicationHandler

): BleCommunicationBase(bleCommunicationHandler) {


    suspend fun readSystemLocked(): BleResult<Boolean> {
        return readCharacteristic(CHARACTERISTIC_SYSTEM_LOCKED_UUID).whenHandle{ byteArray ->
                if(byteArray[0].toInt() > 0){ true }else{ false }
            }
    }


    suspend fun writeSystemLocked(value: Boolean): BleResult<Unit>{
        return writeCharacteristic(CHARACTERISTIC_SYSTEM_LOCKED_UUID, value = value.int.toByteArray())

    }



    suspend fun activateNozzle(): BleResult<Unit>{
        return writeCharacteristic(CHARACTERISTIC_ACTIVATION_UUID, value = true.int.toByteArray())
    }



    suspend fun readTimeSync(): BleResult<LocalDateTime>{
        return readCharacteristic(CHARACTERISTIC_TIME_SYNC_UUID).whenHandle { byteArray ->
                byteArrayToLocalDateTime(byteArray)
            }

    }

    suspend fun writeTimeSync(time: LocalDateTime): BleResult<Unit> {
        val epochSeconds = time.toInstant(TimeZone.UTC).epochSeconds.toInt()

        val timeByteArray = epochSeconds.toByteArray()

        return writeCharacteristic(CHARACTERISTIC_TIME_SYNC_UUID, timeByteArray)
    }



    // mapper

    private fun byteArrayToLocalDateTime(timeByteArray: ByteArray): LocalDateTime {
        require(timeByteArray.size == 8) { "Byte array must be exactly 8 bytes" }
        val unixTime = timeByteArray.foldIndexed(0L) { index, acc, byte ->
            acc or (byte.toLong() and 0xFF shl (7 - index) * 8)
        }
        return Instant.fromEpochSeconds(unixTime).toLocalDateTime(TimeZone.UTC)
    }




    @OptIn(ExperimentalUuidApi::class)
    override val serviceUUID = Uuid.parse(SystemService.SERVICE_UUID)







}




