package io.pc7.ninu.presentation.lab.screen


import io.pc7.ninu.data.ble.model.BleError
import io.pc7.ninu.data.ble.model.BleResult
import io.pc7.ninu.data.ble.repository.PerfumeBleCommunication
import io.pc7.ninu.data.network.repository.PerfumeRepository
import io.pc7.ninu.domain.mapper.toFragrance
import io.pc7.ninu.domain.model.lab.LabFragrance
import io.pc7.ninu.domain.model.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LabMainViewModel(
    private val viewModelScope: CoroutineScope,
    private val perfumeRepository: PerfumeRepository,
    private val perfumeCommunication: PerfumeBleCommunication

) {

    private val _state = MutableStateFlow(initializeState())
    val state = _state.asStateFlow()


    private val eventChannel = Channel<LabMainEvents>()
    val events = eventChannel.receiveAsFlow()


    fun onAction(action: LabMainAction){
        when(action){
            is LabMainAction.OnUpdateIntensity -> _state.update { it.copy(intensity = action.intensity) }
            is LabMainAction.OnSavePercentages -> {savePercentages(action.percentages)}
            LabMainAction.LoadScent -> uploadScent()
        }
    }


    private fun uploadScent(){
        viewModelScope.launch {
            _state.update { it.copy(upload = Resource.Loading) }
            try{
                val fragrances = _state.value.fragrances
                fragrances?.let {
                    eventChannel.send(LabMainEvents.NavigateNext(it.map { it.toFragrance() }.toTypedArray(), _state.value.intensity))
//                    val respond = perfumeCommunication.uploadCartridgesDoseVolume(
//                        cartridge1Percentage = fragrances[0].percentage,
//                        cartridge2Percentage = fragrances[1].percentage,
//                        cartridge3Percentage = fragrances[2].percentage,
//                        intensity = _state.value.intensity
//                    )
//                    _state.update { it.copy(upload = Resource.Result(respond)) }
//                    if(respond is BleResult.Success){
//                        eventChannel.send(LabMainEvents.NavigateNext(it.map { it.toFragrance() }.toTypedArray(), _state.value.intensity))
//                    }
                }


            }catch (e: Exception){
                _state.update { it.copy(upload = Resource.Result(BleResult.Error(BleError.UnknownError()))) }
            }
        }
    }

    private fun savePercentages(percentages: List<Int>){
        val fragrances = _state.value.fragrances
        fragrances?.let {
            _state.update {
                it.copy(
                    fragrances =
                        fragrances.mapIndexed() {index, perfume ->
                            perfume.copy(percentage = percentages[index])
                        }
                )
            }
        }

    }



    init {
        viewModelScope.launch {
//            labHandler.fragrances.collect{ fragrances ->
//                fragrances?.let {
//                    _state.update { it.copy(
//                        fragrances = (fragrances as ResultMy.Success).data
//                    ) }
//                }
//
//            }
        }



    }




    companion object{
        fun initializeState(): LabMainState =
            LabMainState(
                fragrances = listOf(
                    LabFragrance(name = "Casual", percentage = 50, sku = 1),
                    LabFragrance(name = "Work", percentage = 30, sku = 2),
                    LabFragrance(name = "Elegant", percentage = 20, sku = 3),
                ),
                intensity = 1,
            )

    }

}

