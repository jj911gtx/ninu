package io.pc7.ninu.presentation.perfumeMain

sealed class PerfumeMainEvent {
    data class SaveRespond(val success: Boolean): PerfumeMainEvent()

}