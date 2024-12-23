package io.pc7.ninu.data.ble.repository


import io.pc7.ninu.data.ble.BLECommunicationHandler
import io.pc7.ninu.data.ble.mapper.toByteInt
import io.pc7.ninu.data.ble.model.BleResult
import io.pc7.ninu.data.ble.model.PerfumeSN
import io.pc7.ninu.data.ble.service.FragranceIndex
import io.pc7.ninu.data.ble.service.PerfumeService
import io.pc7.ninu.data.ble.service.PerfumeService.CHARACTERISTIC_DOSE_VOLUME_3_UUID
import io.pc7.ninu.data.ble.service.PerfumeService.CHARACTERISTIC_REMAINING_VOLUME_3_UUID
import io.pc7.ninu.data.ble.service.PerfumeService.CHARACTERISTIC_DOSE_VOLUME_2_UUID
import io.pc7.ninu.data.ble.service.PerfumeService.CHARACTERISTIC_REMAINING_VOLUME_2_UUID
import io.pc7.ninu.data.ble.service.PerfumeService.CHARACTERISTIC_DOSE_VOLUME_1_UUID
import io.pc7.ninu.data.ble.service.PerfumeService.CHARACTERISTIC_REMAINING_VOLUME_1_UUID
import io.pc7.ninu.data.ble.service.PerfumeService.CHARACTERISTIC_SKU_1_UUID
import io.pc7.ninu.data.ble.service.PerfumeService.CHARACTERISTIC_SKU_2_UUID
import io.pc7.ninu.data.ble.service.PerfumeService.CHARACTERISTIC_SKU_3_UUID
import io.pc7.ninu.data.ble.service.PerfumeService.CHARACTERISTIC_SN_1_UUID
import io.pc7.ninu.data.ble.service.PerfumeService.CHARACTERISTIC_SN_2_UUID
import io.pc7.ninu.data.ble.service.PerfumeService.CHARACTERISTIC_SN_3_UUID
import io.pc7.ninu.data.ble.service.PerfumeUUIDs
import io.pc7.ninu.data.ble.whenHandle
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class PerfumeBleCommunication(
    bleCommunicationHandler: BLECommunicationHandler
): BleCommunicationBase(
    bleCommunicationHandler
) {




    suspend fun readAllSKUs(): BleResult<List<Int>> {
        val bleFragrancesSKUs = listOf(
            readSKU(FragranceIndex.FRAGRANCE_1),
            readSKU(FragranceIndex.FRAGRANCE_2),
            readSKU(FragranceIndex.FRAGRANCE_3)
        )
        if(!bleFragrancesSKUs.all { it is BleResult.Success }){
            bleFragrancesSKUs.forEach {
                if(it is BleResult.Error){
                    return BleResult.Error(it.type)
                }
            }
        }
        return BleResult.Success(bleFragrancesSKUs.map { (it as BleResult.Success).data })
    }
    suspend fun uploadCartridgesDoseVolume(
        cartridge1Percentage: Int,
        cartridge2Percentage: Int,
        cartridge3Percentage: Int,
        intensity: Int
    ): BleResult<Unit>{
        fun scaleToTarget(values: List<Int>, target: Int): List<Int> {

            val exactScaledValues = values.map { it * target / 100.toDouble() }

            val integerParts = exactScaledValues.map { it.toInt() }
            val fractionalParts = exactScaledValues.map { it - it.toInt() }

            val integerSum = integerParts.sum()
            val remainder = target - integerSum

            val indicesByFraction = fractionalParts
                .withIndex()
                .sortedByDescending { it.value }
                .map { it.index }

            val result = integerParts.toMutableList()
            for (i in 0 until remainder) {
                val index = indicesByFraction[i]
                result[index] += 1
            }
            return result
        }
        val percentages = scaleToTarget(listOf(cartridge1Percentage, cartridge2Percentage, cartridge3Percentage), intensity*10)

        val writeResponds = listOf(
            writeDoseVolume(FragranceIndex.FRAGRANCE_1, percentages[0]),
            writeDoseVolume(FragranceIndex.FRAGRANCE_2, percentages[1]),
            writeDoseVolume(FragranceIndex.FRAGRANCE_3, percentages[2]),
        )
        writeResponds.forEach {
            if(it !is BleResult.Success){
                return it
            }
        }
        return BleResult.Success(Unit)

    }


    suspend fun readSKU(perfumeIndex: FragranceIndex): BleResult<Int> {
        return readCharacteristic(perfumeIndex.getPerfumeUUIDs().sku).whenHandle(
                onSuccess = { byteArray ->
                    byteArray.toByteInt()
                }
            )

    }

    suspend fun readSN(perfumeIndex: FragranceIndex): BleResult<PerfumeSN>{
        return readCharacteristic(perfumeIndex.getPerfumeUUIDs().sn).whenHandle{  byteArray ->
            byteArrayToPerfumeSN(byteArray)
        }

    }


    suspend fun readDoseVolume(perfumeIndex: FragranceIndex): BleResult<Int>{
        return readCharacteristic(perfumeIndex.getPerfumeUUIDs().doseVolume).whenHandle { byteArray ->
                byteArray.toByteInt()
            }

    }

    suspend fun readRemainingVolume(perfumeIndex: FragranceIndex): BleResult<Int> {
        return readCharacteristic(perfumeIndex.getPerfumeUUIDs().remainingVolume).whenHandle { byteArray ->
                if (byteArray.size < 2) throw IllegalArgumentException("Byte array must be at least 2 bytes long")
                ((byteArray[0].toInt() and 0xFF) shl 8) or (byteArray[1].toInt() and 0xFF)
            }

    }


    suspend fun writeDoseVolume(perfumeIndex: FragranceIndex, value: Int): BleResult<Unit> {
        if (value !in 0..255) {
            throw IllegalArgumentException("Value must be between 0 and 255 for an 8-bit unsigned integer.")
        }
        val byteArray = byteArrayOf(value.toUByte().toByte())
        return writeCharacteristic(perfumeIndex.getPerfumeUUIDs().doseVolume, byteArray)

    }





    fun byteArrayToPerfumeSN(byteArray: ByteArray): PerfumeSN {
        var offset = 0

        val type = byteArray.copyOfRange(offset, offset + 3).decodeToString()
        offset += 4

        val quantity = byteArray.copyOfRange(offset, offset + 3).decodeToString().toInt()
        offset += 4

        val date = byteArray.copyOfRange(offset, offset + 4).decodeToString()
            .parseYearMonthToDate()
        offset += 5

        val serial = byteArray.copyOfRange(offset, offset + 3).decodeToString().toInt()
        offset += 4

        val expirationDate = byteArray.copyOfRange(offset, offset + 4).decodeToString()
            .parseYearMonthToDate()
        offset += 5

        val hmac = byteArray.copyOfRange(offset, offset + 16).decodeToString()

        return PerfumeSN(
            type = type,
            quantity = quantity,
            date = date,
            serial = serial,
            expirationDate = expirationDate,
            hmac = hmac
        )
    }


    private fun String.parseYearMonthToDate(): LocalDate {
        require(this.length == 4) { "Date code must be exactly 4 characters in 'yyMM' format" }

        val year = 2000 + this.substring(0, 2).toInt() // Convert "yy" to full year
        val month = this.substring(2, 4).toInt()

        return LocalDate(year, month, 1) // Return the first day of the month
    }


    @OptIn(ExperimentalUuidApi::class)
    override val serviceUUID = Uuid.parse(PerfumeService.SERVICE_UUID)


    private val fragranceUUIDs = mapOf(
        FragranceIndex.FRAGRANCE_1 to PerfumeUUIDs(sku = CHARACTERISTIC_SKU_1_UUID, sn = CHARACTERISTIC_SN_1_UUID, doseVolume =CHARACTERISTIC_DOSE_VOLUME_1_UUID, remainingVolume =CHARACTERISTIC_REMAINING_VOLUME_1_UUID),
        FragranceIndex.FRAGRANCE_2 to PerfumeUUIDs(sku = CHARACTERISTIC_SKU_2_UUID, sn = CHARACTERISTIC_SN_2_UUID, doseVolume =CHARACTERISTIC_DOSE_VOLUME_2_UUID, remainingVolume =CHARACTERISTIC_REMAINING_VOLUME_2_UUID),
        FragranceIndex.FRAGRANCE_3 to PerfumeUUIDs(sku = CHARACTERISTIC_SKU_3_UUID, sn = CHARACTERISTIC_SN_3_UUID, doseVolume =CHARACTERISTIC_DOSE_VOLUME_3_UUID, remainingVolume =CHARACTERISTIC_REMAINING_VOLUME_3_UUID),
    )

    private inline fun FragranceIndex.getPerfumeUUIDs(): PerfumeUUIDs {
        return fragranceUUIDs[this]!!
    }
}



