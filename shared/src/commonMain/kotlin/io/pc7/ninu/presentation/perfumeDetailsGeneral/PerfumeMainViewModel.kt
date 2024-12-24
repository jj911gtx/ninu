package io.pc7.ninu.presentation.perfumeDetailsGeneral


import io.pc7.ninu.data.network.model.navigation.NavigatePerfumeMain
import io.pc7.ninu.data.network.repository.PerfumeRepository
import io.pc7.ninu.domain.model.input.MyInput
import io.pc7.ninu.domain.model.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class PerfumeMainViewModel(
    coroutineScope: CoroutineScope?,
    navigatePerfumeMain: NavigatePerfumeMain,
    private val perfumeRepository: PerfumeRepository
){

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)


    private val _state = MutableStateFlow(initializeState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<PerfumeMainEvent>()
    val events = eventChannel.receiveAsFlow()

    fun action(action: PerfumeMainAction){
        when(action){
            is PerfumeMainAction.OnUpdateName -> _state.update { it.copy(name = it.name.update(action.name)) }
            is PerfumeMainAction.OnSelectIcon -> _state.update { it.copy(selectedIcon = action.iconId) }
            PerfumeMainAction.OnClickDelete -> delete()
//            PerfumeMainAction.OnClickEdit -> TODO()
            PerfumeMainAction.OnClickFavourite -> _state.update { it.copy(isFavourite = !it.isFavourite) }
            PerfumeMainAction.OnClickSave -> save()
        }
    }

    private val id: Int?
    init {
        navigatePerfumeMain.name?.let {
            _state.update { it.copy(
                name = it.name.update(navigatePerfumeMain.name) ,
                staticName = true
            ) }
        }
        id = navigatePerfumeMain.id
        viewModelScope.launch {
            _state.update {
                it.copy(
//                    greatFor = Resource.Result(perfumeRepository.getGreatFor(fragrances = fragrances.map { FragranceBasic(name = "", percentage = it.percentage, sku = it.sku) })),
//                    feelHow = Resource.Result(perfumeRepository.getFeelHow(fragrances = fragrances.map { FragranceBasic(name = "", percentage = it.percentage, sku = it.sku) }))
                )
            }

        }
    }

    private fun save() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(save = Resource.Loading) }
            delay(1000)
            eventChannel.send(PerfumeMainEvent.SaveRespond(true))
            _state.update { it.copy(save = null) }
        }
    }

    private fun delete(){

    }


    companion object {
        fun initializeState(): PerfumeMainState =
            PerfumeMainState(
                MyInput(""),
                selectedIcon = null,
                isFavourite = false,
                greatFor = Resource.Loading,
                feelHow = Resource.Loading,
                staticName = false
            )
    }





}