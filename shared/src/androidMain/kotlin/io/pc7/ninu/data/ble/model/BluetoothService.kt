package io.pc7.ninu.data.ble.model

import android.bluetooth.BluetoothGattService
import java.util.UUID

actual class BluetoothService(
    uuid: UUID?, serviceType: Int

): BluetoothGattService(uuid, serviceType) {

}

fun BluetoothGattService.toBluetoothService(): BluetoothService {
    return BluetoothService(uuid, serviceType = this.type)
}