package io.pc7.ninu.data.network.model.perfumeSelection

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PerfumeSelectionWhereToRespond(
    @SerialName("work") val work: List<PerfumeSelectionItem>,
    @SerialName("casual") val casual: List<PerfumeSelectionItem>,
    @SerialName("elegant") val elegant: List<PerfumeSelectionItem>,
)


