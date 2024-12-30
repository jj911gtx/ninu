package io.pc7.ninu.presentation.pairing.scan

sealed class ScanEvent {
    data class NavNext(val deviceMac: String): ScanEvent()
}