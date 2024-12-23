package io.pc7.ninu.presentation.pairing.scan

sealed class ScanEvent {
    data object NavNext: ScanEvent()
}