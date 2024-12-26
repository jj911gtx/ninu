package io.pc7.ninu.presentation.pairing.purchaseInfo

import io.pc7.ninu.domain.model.input.MyInput
import kotlinx.datetime.LocalDate

data class PurchaseInfoState(
    val whereBought: MyInput<String> = MyInput(""),
    val dateOfPurchase: MyInput<LocalDate?> = MyInput(null),
    val proofOfPurchase: MyInput<Unit?> = MyInput(null),
    val deviceConnected: Boolean = false,
)