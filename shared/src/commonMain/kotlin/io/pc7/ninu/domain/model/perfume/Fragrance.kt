package io.pc7.ninu.domain.model.perfume

import kotlinx.serialization.Serializable


@Serializable
data class Fragrance(
    val sku: Int,
    val percentage: Int,
)
