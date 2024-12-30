package io.pc7.ninu.presentation.pairing.purchaseInfo


import io.pc7.ninu.data.ble.model.BleResult
import io.pc7.ninu.data.ble.repository.BleCommunication
import io.pc7.ninu.data.network.repository.GeneralRepository
import io.pc7.ninu.database.Dao
import io.pc7.ninu.domain.model.util.Resource
import io.pc7.ninu.domain.usecase.SaveDeviceToDatabase
import io.pc7.ninu.presentation.pairing.purchaseInfo.PurchaseInfoViewModel.Screen2Event
import io.pc7.ninu.presentation.util.ViewModelBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PurchaseInfoViewModel(
    coroutineScope: CoroutineScope?,
    private val deviceMac: String,
    private val bleCommunication: BleCommunication,
    private val dao: Dao,
    private val generalRepository: GeneralRepository,
): ViewModelBase<PurchaseInfoState, PurchaseInfoAction, Screen2Event>(
    coroutineScope,
    PurchaseInfoState()
) {


    private var imageByteArray: ByteArray? = null

    override fun action(action: PurchaseInfoAction) {
        when (action) {
            is PurchaseInfoAction.OnDateOfPurchaseUpdate -> _state.update { it.copy(dateOfPurchase = it.dateOfPurchase.update(action.value)) }
            is PurchaseInfoAction.OnProofOfPurchaseUpdate -> {
                imageByteArray = action.value
                _state.update { it.copy(proofOfPurchase = it.proofOfPurchase.update(Unit)) }
            }
            is PurchaseInfoAction.OnWhereBoughtUpdate -> _state.update { it.copy(whereBought = it.whereBought.update(action.value)) }
            PurchaseInfoAction.OnRegister -> confirmRegistration()
        }
    }

    fun confirmRegistration(){
        viewModelScope.launch {
            eventChannel.send(Screen2Event.RegistrationConfirmed)
        }
    }

    init {
        connect()
    }


    private var coroutine: Job? = null
    fun connect(){
        coroutine?.cancel()
        coroutine = viewModelScope.launch(Dispatchers.IO) {
            if(bleCommunication.isBluetoothEnabled()){
                while (true){
                    val result = bleCommunication.connect(/*deviceMac*/)
                    updateState { it.copy(
                        deviceConnected = when(result){
                            is BleResult.Error -> { Resource.Result(result)}
                            is BleResult.Success -> {
                                SaveDeviceToDatabase(dao = dao, generalRepository = generalRepository).invoke(deviceMac)
                                coroutine?.cancel()
                                Resource.Result(result)

                            }
                        }
                    )}
                    delay(1000)
                }
            }else{
                eventChannel.send(Screen2Event.EnableBluetooth)
            }

        }
    }




    sealed class Screen2Event {
        data object EnableBluetooth: Screen2Event()
        data object RegistrationConfirmed: Screen2Event()
    }


}