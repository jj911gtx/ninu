package io.pc7.ninu.data.network.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UpdatePasswordRequest(
    @SerialName("password") val password: String,
    @SerialName("new_password") val newPassword: String,
)
