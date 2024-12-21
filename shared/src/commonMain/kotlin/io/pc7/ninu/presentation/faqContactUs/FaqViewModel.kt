package io.pc7.ninu.presentation.faqContactUs


import io.pc7.ninu.data.network.model.FaqModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FaqViewModel(
    coroutineScope: CoroutineScope? = null
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(FaqState())
    val state: StateFlow<FaqState> get() = _state


    companion object{
        fun initializeState(): FaqState{
            return FaqState(
                faqs = listOf(FaqModel("What is NINU", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."))
            )
        }
    }
}

data class FaqState(
    val faqs: List<FaqModel> = emptyList()
)

