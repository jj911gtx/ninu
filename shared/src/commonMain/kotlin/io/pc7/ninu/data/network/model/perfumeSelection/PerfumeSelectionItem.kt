package io.pc7.ninu.data.network.model.perfumeSelection

import kotlinx.serialization.Serializable


@Serializable
data class PerfumeSelectionItem(
    val name: String,
    val iconLink: String,
    val fragrances: List<Fragrance>,
    val id: Int
){

    @Serializable
    data class Fragrance(
        val percentage: Int,
        val sku: Int
    )


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as PerfumeSelectionItem

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}




