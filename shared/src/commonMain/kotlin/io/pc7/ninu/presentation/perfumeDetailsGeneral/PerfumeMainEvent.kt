package io.pc7.ninu.presentation.perfumeDetailsGeneral

sealed class PerfumeMainEvent {
    data class SaveRespond(val success: Boolean): PerfumeMainEvent()

}