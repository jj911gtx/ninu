package io.pc7.ninu.domain.mapper

import io.pc7.ninu.domain.model.lab.LabFragrance
import io.pc7.ninu.domain.model.perfume.Fragrance
import io.pc7.ninu.domain.model.perfumeSelection.PerfumeSelectionDisplay


fun LabFragrance.toFragrance(): Fragrance{
    return Fragrance(
        sku = sku,
        percentage = percentage
    )
}



fun PerfumeSelectionDisplay.Fragrance.toFragrance(): Fragrance{
    return Fragrance(
        sku = sku,
        percentage = percentage
    )
}