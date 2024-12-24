package io.pc7.ninu.presentation.perfumeSelection

import io.pc7.ninu.data.network.error.DataError
import io.pc7.ninu.data.network.model.navigation.NavigatePerfumeMain
import io.pc7.ninu.data.network.repository.PerfumeRepository
import io.pc7.ninu.domain.model.util.Resource
import io.pc7.ninu.domain.model.util.ResultMy
import io.pc7.ninu.domain.usecase.ScalePercentagesToVolume
import io.pc7.ninu.presentation.util.ViewModelBase
import kotlinx.coroutines.CoroutineScope
import io.pc7.ninu.data.network.model.perfumeSelection.PerfumeSelectionItem
import io.pc7.ninu.data.network.model.perfumeSelection.PerfumeSelectionSkuDoseVolume
import io.pc7.ninu.domain.mapper.toFragrance
import io.pc7.ninu.domain.model.perfumeSelection.PerfumeSelectionDisplay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class PerfumeSelectionViewModel(
    coroutineScope: CoroutineScope?,
    protected val perfumeRepository: PerfumeRepository,
): ViewModelBase<PerfumeSelectionState, PerfumeSelectionViewModel.Action, PerfumeSelectionViewModel.Event>(
    coroutineScope,
    PerfumeSelectionState()
) {
    sealed class Action{
        data class SelectItem(val perfume: PerfumeSelectionDisplay): Action()
    }
    sealed class Event{
        data class Navigate(val perfumeData: NavigatePerfumeMain): Event()
    }


    abstract val headers: List<String>

    override fun action(action: Action) {
        when(action){
            is Action.SelectItem -> {
                updateState { it.copy(selectedItem = action.perfume) }
                viewModelScope.launch(Dispatchers.IO) {
                    delay(10)//TODO
                    val perfume = action.perfume
                    eventChannel.send(Event.Navigate(NavigatePerfumeMain(
                        fragrances = perfume.fragrances.map { it.toFragrance() }.toTypedArray(),
                        id = perfume.id,
                        name = perfume.name
                    )))
                    _state.update { it.copy(selectedItem = null) }
                }
            }
        }
    }

    init {
//        viewModelScope.launch {
//            val skusDoseVolume = perfumeRepository.getSKUsWithRemainingVolumeFromBle()
//            when(skusDoseVolume){
//                is BleResult.Error -> _state.update { it.copy(errors = skusDoseVolume.type) }
//                is BleResult.Success -> {
//                    val perfumes = getPerfumes()
//                    when(perfumes){
//                        is ResultMy.Error -> _state.update { it.copy(errors = perfumes.error) }
//                        is ResultMy.Success -> {
//                            val perfumes:  List<List<PerfumeSelectionItem>> = perfumes.data
//                            val skusDoseVolume: List<PerfumeSelectionSkuDoseVolume> = skusDoseVolume.data
//
//                            val perfumesDisplay = perfumes.map {
//                                processPerfumeSelectionItems(
//                                    perfumes = it, skuDoseVolumes = skusDoseVolume
//                                )
//                            }
//                            _state.update { it.copy(perfumes = Resource.Result(ResultMy.Success(perfumesDisplay))) }
//                        }
//                    }
//                }
//            }
//        }
    }

    fun processPerfumeSelectionItems(
        perfumes: List<PerfumeSelectionItem>,
        skuDoseVolumes: List<PerfumeSelectionSkuDoseVolume>,
    ): List<PerfumeSelectionDisplay> {
        val skuToVolumeMap = skuDoseVolumes.associateBy { it.sku }

        val scalePercentagesToVolume = ScalePercentagesToVolume()

        return perfumes.map { perfume ->
            val scaledVolumes = scalePercentagesToVolume(
                perfume.fragrances.map { it.percentage },
            )

            val fragrances = perfume.fragrances.zip(scaledVolumes).map { (fragrance, volume) ->
                PerfumeSelectionDisplay.Fragrance(
                    sku = fragrance.sku,
                    enough = (skuToVolumeMap[fragrance.sku]?.doseVolume ?: 0) >= volume,
                    percentage = fragrance.percentage
                )
            }

            PerfumeSelectionDisplay(
                name = perfume.name,
                id = perfume.id,
                iconLink = perfume.iconLink,
                fragrances = fragrances
            )
        }
    }



    /**
     * mozne tezave da se ne zamenja vrstni red sectionu*/
    abstract suspend fun getPerfumes(): ResultMy<List<List<PerfumeSelectionItem>>, DataError.Network>

}

data class PerfumeSelectionState(
    val errors: Error? = null,
    val perfumes: Resource<ResultMy<PerfumesSelections, DataError.Network>> = Resource.Result(ResultMy.Success(
        PerfumesSelections(
        perfumes = List(3){List(10){ index ->
            PerfumeSelectionDisplay(
                name = "hello", id = index, iconLink = "", fragrances = List(3){
                    PerfumeSelectionDisplay.Fragrance(
                    enough = true, sku = 1, percentage = 20
                )
                }

            )
        } }
    ))),//Resource.Loading,
    val selectedItem: PerfumeSelectionDisplay? = null
){


    data class PerfumesSelections(
        val perfumes: List<List<PerfumeSelectionDisplay>>
    )

    companion object{
        fun create() = PerfumeSelectionState()
    }
}


