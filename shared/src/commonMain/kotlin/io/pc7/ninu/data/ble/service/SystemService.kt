package core.bluetooth.services

import io.pc7.ninu.data.ble.model.BluetoothCharacteristic
import io.pc7.ninu.data.ble.model.BluetoothService
import io.pc7.ninu.data.ble.service.NINUBleService


data class SystemService(
    val service: BluetoothService,
    val systemLocked : BluetoothCharacteristic,
    val activation : BluetoothCharacteristic,
    val timeSync : BluetoothCharacteristic,
): NINUBleService {


    companion object{
        const val SERVICE_UUID = "00003000-0000-1000-8000-00805f9b34fb"//"00003000-8314-11e9-bc42-526af7764f64"
        const val CHARACTERISTIC_SYSTEM_LOCKED_UUID = "00003101-0000-1000-8000-00805f9b34fb"//"00003001-8314-11e9-bc42-526af7764f64"
        const val CHARACTERISTIC_ACTIVATION_UUID = "00003102-0000-1000-8000-00805f9b34fb"//"00003002-8314-11e9-bc42-526af7764f64"
        const val CHARACTERISTIC_TIME_SYNC_UUID = "00003103-0000-1000-8000-00805f9b34fb"//"00003103-8314-11e9-bc42-526af7764f64"
    }
}



