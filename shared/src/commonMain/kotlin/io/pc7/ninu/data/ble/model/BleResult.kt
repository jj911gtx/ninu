package io.pc7.ninu.data.ble.model

sealed class BleResult<out T> {
    data class Success<out T>(val data: T) : BleResult<T>()
    data class Error(val type: BleError) : BleResult<Nothing>()
}