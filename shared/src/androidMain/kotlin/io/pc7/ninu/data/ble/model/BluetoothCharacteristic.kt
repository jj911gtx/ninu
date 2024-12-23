package io.pc7.ninu.data.ble.model

import android.bluetooth.BluetoothGattCharacteristic
import java.util.UUID

actual class BluetoothCharacteristic(
    uuid: UUID?, properties: Int, permissions: Int
//    val characteristic: BluetoothGattCharacteristic
): BluetoothGattCharacteristic(uuid, properties, permissions) {


}

fun BluetoothGattCharacteristic.toBluetoothCharacteristic(): BluetoothCharacteristic {
    return BluetoothCharacteristic(uuid, properties, permissions)
}
