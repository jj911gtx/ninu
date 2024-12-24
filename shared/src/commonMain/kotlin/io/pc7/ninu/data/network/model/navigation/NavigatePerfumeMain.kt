package io.pc7.ninu.data.network.model.navigation

import io.pc7.ninu.domain.model.perfume.Fragrance


data class NavigatePerfumeMain(
    val fragrances: Array<Fragrance>,
    val id: Int,
    val name: String,
)
