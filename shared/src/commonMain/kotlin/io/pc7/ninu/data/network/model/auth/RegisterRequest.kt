package io.pc7.ninu.data.network.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RegisterRequest(
    @SerialName("first_name") val name: String,
    @SerialName("last_name") val surname: String,
    @SerialName("email") val email: String,
    @SerialName("password") val password: String
)
