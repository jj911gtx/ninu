package io.pc7.ninu.domain.mapper

import io.pc7.ninu.domain.model.lab.LabFragrance
import io.pc7.ninu.domain.model.perfume.Fragrance


fun LabFragrance.toFragrance(): Fragrance{
    return Fragrance(
        sku = sku,
        percentage = percentage
    )
}