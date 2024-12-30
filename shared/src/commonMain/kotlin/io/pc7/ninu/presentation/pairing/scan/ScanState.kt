package io.pc7.ninu.presentation.pairing.scan

import io.pc7.ninu.data.network.error.DataError
import io.pc7.ninu.domain.model.input.MyInput
import io.pc7.ninu.domain.model.util.Resource


data class ScanState(
    val serialNumber: MyInput<String> = MyInput(""),
    val serialNumberRespond: Resource<DataError.Network>? = null
)