package io.pc7.ninu.presentation.pairing.scan

sealed class ScanAction {
    data class OnSerialNumberUpdate(val sn: String): ScanAction()
    data object OnProceed: ScanAction()
}