package io.pc7.ninu.presentation.favourites


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EditFavouritesViewModel(
    coroutineScope: CoroutineScope? = null
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(EditFavouritesState())
    val state: StateFlow<EditFavouritesState> get() = _state

    private val eventChannel = Channel<EditFavouritesEvent>()
    val events = eventChannel.receiveAsFlow()

    fun action(action: EditFavouritesAction) {
//        when(action){
//            
//        }
    }
}

data class EditFavouritesState(
    val selections: List<String> = emptyList(),
    val favourites: List<String> = emptyList()
)


sealed class EditFavouritesEvent {

}

sealed class EditFavouritesAction {

}
