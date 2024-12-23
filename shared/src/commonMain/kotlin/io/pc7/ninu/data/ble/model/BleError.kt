package io.pc7.ninu.data.ble.model

sealed class BleError: Error() {
    data object ConnectionCongested : BleError()
    data object Disconnected: BleError()
    data object Connecting: BleError()
    data class Failure(val code: Int) : BleError()
    data class UnknownError(val code: Int? = null) : BleError()

    object ConnectionTimeout : BleError()
    object ConnectionFailed : BleError()
    object NoAddressInitialized: BleError()
}