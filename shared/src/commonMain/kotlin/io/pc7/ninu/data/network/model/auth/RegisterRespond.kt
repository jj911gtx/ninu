package io.pc7.ninu.data.network.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRespond(
    @SerialName("token") val token: String,
)
