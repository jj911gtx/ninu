package io.pc7.ninu.di

import feature.pairing.screens.purchaseInfo.PurchaseInfoViewModelAndroid
import io.pc7.ninu.presentation.activities.PairingActivity
import io.pc7.ninu.presentation.pairing.screens.scan.ScanViewModelAndroid
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.definition.Kind
import org.koin.core.scope.Scope
import org.koin.dsl.module


val pairingDi = module {
    scope<PairingActivity>(){
        viewModelOf(::PurchaseInfoViewModelAndroid)
        viewModelOf(::ScanViewModelAndroid)
    }
}