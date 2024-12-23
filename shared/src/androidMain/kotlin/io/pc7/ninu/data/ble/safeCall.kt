package io.pc7.ninu.data.ble

import android.bluetooth.BluetoothGatt
import io.pc7.ninu.data.ble.model.BleError
import io.pc7.ninu.data.ble.model.BleException
import io.pc7.ninu.data.ble.model.BleResult

suspend fun <T> safeCall(
    gattOperation: suspend () -> T
): BleResult<T> {
    return try {
        val result = gattOperation()
        if (result != null) {
            BleResult.Success(result)
        } else {
            BleResult.Error(BleError.UnknownError())
        }
    } catch (e: BleException) {
        val errorType = when (e.status) {
            BluetoothGatt.STATE_DISCONNECTED -> BleError.Disconnected
            BluetoothGatt.STATE_CONNECTING -> BleError.Connecting
            BluetoothGatt.STATE_DISCONNECTING -> BleError.Disconnected

            BluetoothGatt.GATT_CONNECTION_CONGESTED -> BleError.ConnectionCongested

            BluetoothGatt.GATT_FAILURE -> BleError.Failure(e.status)
            else -> BleError.UnknownError(e.status)
        }
        BleResult.Error(errorType)
    }
}