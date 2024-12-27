package io.pc7.ninu.domain.mapper

import io.pc7.ninu.domain.model.lab.DonutChartItem
import io.pc7.ninu.domain.model.lab.LabFragrance

fun LabFragrance.toDonutChartItem(color1: Long, color2: Long): DonutChartItem {
    return DonutChartItem(
        colors = Pair(color1, color2),
        percentage = percentage.toFloat(),
        id = sku
    )
}