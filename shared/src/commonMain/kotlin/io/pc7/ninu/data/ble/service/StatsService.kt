package io.pc7.ninu.data.ble.service


import io.pc7.ninu.data.ble.model.BluetoothService
import io.pc7.ninu.data.ble.model.BluetoothCharacteristic


data class StatsService(
    val service: BluetoothService,
    val record : BluetoothCharacteristic,
): NINUBleService {


    companion object{
        const val SERVICE_UUID = "00005000-0000-1000-8000-00805f9b34fb"//"00005000-8314-11e9-bc42-526af7764f64"
        const val CHARACTERISTIC_STATS_UUID = "00005000-0000-1000-8000-00805f9b34fb"//"00005001-8314-11e9-bc42-526af7764f64"
    }
}