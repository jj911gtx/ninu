package io.pc7.ninu.domain.model.perfume

import kotlinx.datetime.LocalDateTime

data class PerfumeUseData(
    val name: String,
    val time: LocalDateTime,
)
