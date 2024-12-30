package io.pc7.ninu.presentation.pairing.screens.purchaseInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pc7.ninu.data.ble.repository.BleCommunication
import io.pc7.ninu.data.network.repository.GeneralRepository
import io.pc7.ninu.database.Dao
import io.pc7.ninu.presentation.pairing.purchaseInfo.PurchaseInfoViewModel

class PurchaseInfoViewModelAndroid(
    deviceMac: String,
    bleCommunication: BleCommunication,
    dao: Dao,
    generalRepository: GeneralRepository
): ViewModel() {
    val viewModel = PurchaseInfoViewModel(
        deviceMac = deviceMac,
        coroutineScope = viewModelScope,
        bleCommunication = bleCommunication,
        dao = dao,
        generalRepository = generalRepository,

    )
}