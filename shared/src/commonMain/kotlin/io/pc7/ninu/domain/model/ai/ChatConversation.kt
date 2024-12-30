package io.pc7.ninu.domain.model.ai

sealed class ChatConversation {
    data class MyText(val text: String): ChatConversation()
    data class AiText(val text: String): ChatConversation()
}