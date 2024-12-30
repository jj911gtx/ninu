package io.pc7.ninu.presentation.ai


import io.pc7.ninu.presentation.util.ViewModelBase
import kotlinx.coroutines.CoroutineScope

class AiSurpriseMeViewModel(
    coroutineScope: CoroutineScope?,
) : ViewModelBase<AiSurpriseMeState, AiSurpriseMeAction, AiSurpriseMeEvent>(
    coroutineScope,
    AiSurpriseMeState()
) {

    override fun action(action: AiSurpriseMeAction) {
//        when(action){
//            
//        }
    }
}

data class AiSurpriseMeState(
    val x: Int = 1,
)


sealed class AiSurpriseMeEvent {

}

sealed class AiSurpriseMeAction {

}
