package io.pc7.ninu.presentation.lab.screen

import io.pc7.ninu.data.ble.model.BleResult
import io.pc7.ninu.domain.model.lab.LabFragrance
import io.pc7.ninu.domain.model.util.Resource


data class LabMainState(
    val fragrances: List<LabFragrance>? = null,
    val intensity: Int = 1,
    val upload: Resource<BleResult<Unit>>? = null
)


data class PerfumePercentageEditor(
    val index: Int,
    val startAngle: Double,
    val endAngle: Double,

)



