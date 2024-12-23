package io.pc7.ninu.domain.mapper

import io.pc7.ninu.domain.model.lab.DonutChartItem
import io.pc7.ninu.domain.model.lab.LabFragrance

fun LabFragrance.toDonutChartItem(color: Long): DonutChartItem {
    return DonutChartItem(
        color = color,
        percentage = percentage.toFloat(),
        id = sku
    )
}