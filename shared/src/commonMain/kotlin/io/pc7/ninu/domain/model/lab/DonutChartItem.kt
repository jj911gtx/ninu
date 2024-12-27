package io.pc7.ninu.domain.model.lab

data class DonutChartItem(
    val colors: Pair<Long, Long>,
    val percentage: Float,
    val id: Int,
)