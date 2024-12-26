package io.pc7.ninu.presentation.pairing.purchaseInfo

import kotlinx.datetime.LocalDate

sealed class PurchaseInfoAction {
        data class OnWhereBoughtUpdate(val value: String): PurchaseInfoAction()
        data class OnDateOfPurchaseUpdate(val value: LocalDate?): PurchaseInfoAction()
        data class OnProofOfPurchaseUpdate(val value: ByteArray): PurchaseInfoAction()
    }