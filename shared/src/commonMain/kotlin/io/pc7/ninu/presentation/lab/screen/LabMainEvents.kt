package io.pc7.ninu.presentation.lab.screen

import io.pc7.ninu.domain.model.lab.LabFragrance
import io.pc7.ninu.domain.model.perfume.Fragrance

sealed class LabMainEvents {
    data class NavigateNext(
        val fragrances: Array<Fragrance>,
        val intensity: Int,
    ): LabMainEvents()
}