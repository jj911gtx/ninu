package io.pc7.ninu.presentation.perfumeMain


import io.pc7.ninu.data.network.error.DataError
import io.pc7.ninu.domain.model.input.MyInput
import io.pc7.ninu.domain.model.util.Resource
import io.pc7.ninu.domain.model.util.ResultMy


data class PerfumeMainState(
    val name: MyInput<String>,
    val selectedIcon: Int?,
    val isFavourite: Boolean,
    val greatFor: Resource<ResultMy<String, DataError.Network>>,
    val feelHow: Resource<ResultMy<String, DataError.Network>>,
    val updateCurrentPerfume: Boolean = false,
    val deleteAvailable: Boolean = false,
    val save: Resource<DataError.Network>? = null
)

