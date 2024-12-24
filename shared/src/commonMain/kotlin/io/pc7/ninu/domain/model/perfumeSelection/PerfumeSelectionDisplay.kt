package io.pc7.ninu.domain.model.perfumeSelection

data class PerfumeSelectionDisplay(
    val name: String,
    val id: Int,
    val iconLink: String,
    val fragrances: List<Fragrance>
){
    data class Fragrance(
        val enough: Boolean,
        val sku: Int,
        val percentage: Int,
    )
}
