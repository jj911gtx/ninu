package io.pc7.ninu.presentation.pairing.purchaseInfo

import io.pc7.ninu.data.ble.model.BleResult
import io.pc7.ninu.domain.model.input.MyInput
import io.pc7.ninu.domain.model.util.Resource
import io.pc7.ninu.domain.model.util.ResultMy
import kotlinx.datetime.LocalDate

data class PurchaseInfoState(
    val whereBought: MyInput<String> = MyInput(""),
    val dateOfPurchase: MyInput<LocalDate?> = MyInput(null),
    val proofOfPurchase: MyInput<Unit?> = MyInput(null),
    val deviceConnected: Resource<BleResult<Unit>> = Resource.Loading,
)