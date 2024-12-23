package io.pc7.ninu.presentation.pairing.scan

import io.pc7.ninu.domain.model.input.MyInput


data class ScanState(
        val serialNumber: MyInput<String> = MyInput("")
    )