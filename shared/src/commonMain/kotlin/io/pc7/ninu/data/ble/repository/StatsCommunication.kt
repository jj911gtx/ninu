package io.pc7.ninu.data.ble.repository

import io.pc7.ninu.data.ble.BLECommunicationHandler
import io.pc7.ninu.data.ble.model.BleResult
import io.pc7.ninu.data.ble.model.Record
import io.pc7.ninu.data.ble.service.StatsService
import io.pc7.ninu.data.ble.service.StatsService.Companion.CHARACTERISTIC_STATS_UUID
import io.pc7.ninu.data.ble.whenHandle
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


class StatsCommunication(
    bleCommunicationHandler: BLECommunicationHandler,

): BleCommunicationBase(bleCommunicationHandler) {

    private val _record = MutableSharedFlow<String>()
    val record = _record.asSharedFlow()
    private val _recordAmount = MutableSharedFlow<String>()
    val recordAmount = _recordAmount.asSharedFlow()





    suspend fun readRecord(): BleResult<String> {
        return readCharacteristic(CHARACTERISTIC_STATS_UUID).whenHandle { byteArray ->
                csvByteArrayToRecords(byteArray).toString()
            }

    }

    suspend fun writeRecord(): BleResult<Unit>{
        return writeCharacteristic(CHARACTERISTIC_STATS_UUID, byteArrayOf("".toByte()))

    }




    suspend fun notify(value: ByteArray){
        if(value.size > 5){
            readRecord(value)
        }else{
            readRecordNumber(value)
        }
    }

    suspend fun readRecord(value: ByteArray){
        val record = csvByteArrayToRecords(value).toString()
    }

    suspend fun readRecordNumber(value: ByteArray){
        val amount = value.firstOrNull()
            ?.toUByte()
            ?.toInt()
    }



    //mapper

    private fun csvByteArrayToRecords(byteArray: ByteArray): List<Record> {
        val csvContent = byteArray.decodeToString()

        val lines = csvContent.lines().drop(1) // Drop the header line

        return lines.mapNotNull { line ->
            val parts = line.split(";")

            if (parts.size == 7) {
                val timeEpoch = parts[0].toLongOrNull() ?: return@mapNotNull null
                val sku1 = parts[1].toIntOrNull() ?: return@mapNotNull null
                val volume1 = parts[2].toIntOrNull() ?: return@mapNotNull null
                val sku2 = parts[3].toIntOrNull() ?: return@mapNotNull null
                val volume2 = parts[4].toIntOrNull() ?: return@mapNotNull null
                val sku3 = parts[5].toIntOrNull() ?: return@mapNotNull null
                val volume3 = parts[6].toIntOrNull() ?: return@mapNotNull null

                Record(
                    time = timeEpoch,
                    sku1 = sku1,
                    volume1 = volume1,
                    sku2 = sku2,
                    volume2 = volume2,
                    sku3 = sku3,
                    volume3 = volume3
                )
            } else {
                null
            }
        }
    }


    @OptIn(ExperimentalUuidApi::class)
    override val serviceUUID: Uuid = Uuid.parse(StatsService.SERVICE_UUID)

}


