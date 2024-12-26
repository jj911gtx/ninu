package io.pc7.ninu.presentation.pairing.purchaseInfo


import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class PurchaseInfoViewModel(
) {

    private val _state = MutableStateFlow(PurchaseInfoState())
    val state: StateFlow<PurchaseInfoState> get() = _state

    private val eventChannel = Channel<Screen2Event>()
    val events = eventChannel.receiveAsFlow()


    private var imageByteArray: ByteArray? = null

    fun action(action: PurchaseInfoAction) {
        when (action) {
            is PurchaseInfoAction.OnDateOfPurchaseUpdate -> _state.update { it.copy(dateOfPurchase = it.dateOfPurchase.update(action.value)) }
            is PurchaseInfoAction.OnProofOfPurchaseUpdate -> {
                imageByteArray = action.value
                _state.update { it.copy(proofOfPurchase = it.proofOfPurchase.update(Unit)) }
            }
            is PurchaseInfoAction.OnWhereBoughtUpdate -> _state.update { it.copy(whereBought = it.whereBought.update(action.value)) }
        }
    }





    sealed class Screen2Event {

    }


}