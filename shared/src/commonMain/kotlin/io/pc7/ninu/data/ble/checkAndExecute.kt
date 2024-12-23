package io.pc7.ninu.data.ble

import io.pc7.ninu.data.ble.model.BleError
import io.pc7.ninu.data.ble.model.BleResult
import io.pc7.ninu.data.ble.service.NINUBleService

suspend fun <S : NINUBleService, T> S?.checkAndExecute(
    block: suspend (S) -> BleResult<T>
): BleResult<T> {
    return this?.let {
        block(it)
    } ?: BleResult.Error(BleError.Disconnected)
}

fun <T> BleResult<ByteArray>.whenHandle(
    onSuccess: (ByteArray) -> T
): BleResult<T> {
    return when (this) {
        is BleResult.Error -> this
        is BleResult.Success -> BleResult.Success(onSuccess(data))
    }
}
