package io.pc7.ninu.presentation.ai


import io.pc7.ninu.domain.model.ai.ChatConversation
import io.pc7.ninu.domain.model.input.MyInput
import io.pc7.ninu.presentation.util.ViewModelBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AiChatViewModel(
    coroutineScope: CoroutineScope?,
) : ViewModelBase<AiChatState, AiChatAction, AiChatEvent>(coroutineScope, AiChatState()) {

    override fun action(action: AiChatAction) {
        when(action){
            is AiChatAction.OnTextUpdate -> updateState { it.copy(text = it.text.update(action.text)) }
            AiChatAction.OnSendText -> sendText()
        }
    }

    private fun sendText(){
        val text = _state.value.text.value
        if(text.isNotEmpty()){
            updateState { it.copy(
                text = it.text.update(""),
                chatHistory = it.chatHistory + ChatConversation.MyText(text)
            )}
            viewModelScope.launch(Dispatchers.IO) {
                delay(1000)
                updateState { it.copy(chatHistory = it.chatHistory + ChatConversation.AiText("For a romantic dinner, the ideal scent should evoke intimacy, warmth, and sophistication without being overpowering. Opt for a fragrance that combines sensual notes like vanilla, musk, or amber with subtle floral or woody undertones. These create a lasting impression while remaining elegant and inviting.\u2028\u2028Pro Tip: Apply sparingly—one or two sprays on pulse points (wrists, neck) are enough to create an alluring aura without overwhelming the room.") ) }
            }
        }
    }

}

data class AiChatState(
    val text: MyInput<String> = MyInput(""),
    val chatHistory: List<ChatConversation> = emptyList()
)


sealed class AiChatEvent {

}

sealed class AiChatAction {
    data class OnTextUpdate(val text: String): AiChatAction()
    data object OnSendText: AiChatAction()
}
