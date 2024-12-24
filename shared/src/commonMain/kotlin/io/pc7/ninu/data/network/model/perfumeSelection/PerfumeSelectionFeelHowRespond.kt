package io.pc7.ninu.data.network.model.perfumeSelection

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PerfumeSelectionFeelHowRespond(
    @SerialName("fresh") val fresh: List<PerfumeSelectionItem>,
    @SerialName("inspired") val inspired: List<PerfumeSelectionItem>,
    @SerialName("sensual") val sensual: List<PerfumeSelectionItem>,
)


