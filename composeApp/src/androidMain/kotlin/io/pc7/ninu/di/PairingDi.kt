package io.pc7.ninu.di

import io.pc7.ninu.presentation.pairing.screens.purchaseInfo.PurchaseInfoViewModelAndroid
import io.pc7.ninu.presentation.activities.PairingActivity
import io.pc7.ninu.presentation.pairing.screens.scan.ScanViewModelAndroid
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val pairingDi = module {
    scope<PairingActivity>(){
        viewModel{ (deviceMac: String) ->
            PurchaseInfoViewModelAndroid(
                deviceMac = deviceMac,
                bleCommunication = get(),
                dao = get(),
                generalRepository = get(),

            )
        }
        viewModelOf(::ScanViewModelAndroid)
    }
}