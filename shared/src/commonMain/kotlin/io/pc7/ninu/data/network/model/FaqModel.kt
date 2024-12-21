package io.pc7.ninu.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class FaqModel(
    @SerialName("title") val title: String,
    @SerialName("description") val description: String
)
