package io.pc7.ninu.data.ble.service

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class PerfumeUUIDs(
    val sku : String,
    val sn : String,
    val doseVolume : String,
    val remainingVolume : String,
)

object PerfumeService{
    const val SERVICE_UUID = "00005000-0000-1000-8000-00805f9b34fb"//"00004000-8314-11e9-bc42-526af7764f64"

    const val CHARACTERISTIC_SKU_1_UUID = "00004101-8314-11e9-bc42-526af7764f64"
    const val CHARACTERISTIC_SN_1_UUID = "00004102-8314-11e9-bc42-526af7764f64"
    const val CHARACTERISTIC_DOSE_VOLUME_1_UUID = "00004103-8314-11e9-bc42-526af7764f64"
    const val CHARACTERISTIC_REMAINING_VOLUME_1_UUID = "00004104-8314-11e9-bc42-526af7764f64"

    const val CHARACTERISTIC_SKU_2_UUID = "00004201-8314-11e9-bc42-526af7764f64"
    const val CHARACTERISTIC_SN_2_UUID = "00004202-8314-11e9-bc42-526af7764f64"
    const val CHARACTERISTIC_DOSE_VOLUME_2_UUID = "00004203-8314-11e9-bc42-526af7764f64"
    const val CHARACTERISTIC_REMAINING_VOLUME_2_UUID = "00004204-8314-11e9-bc42-526af7764f64"

    const val CHARACTERISTIC_SKU_3_UUID = "00004301-8314-11e9-bc42-526af7764f64"
    const val CHARACTERISTIC_SN_3_UUID = "00004302-8314-11e9-bc42-526af7764f64"
    const val CHARACTERISTIC_DOSE_VOLUME_3_UUID = "00004303-8314-11e9-bc42-526af7764f64"
    const val CHARACTERISTIC_REMAINING_VOLUME_3_UUID = "00004304-8314-11e9-bc42-526af7764f64"

}


enum class FragranceIndex{
    FRAGRANCE_1,
    FRAGRANCE_2,
    FRAGRANCE_3;
}

