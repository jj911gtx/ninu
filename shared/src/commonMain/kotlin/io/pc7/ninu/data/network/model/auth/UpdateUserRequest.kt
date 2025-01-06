package io.pc7.ninu.data.network.model.auth

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UpdateUserRequest(
    @SerialName("first_name") val name: String,
    @SerialName("last_name") val surname: String,
    @SerialName("date_of_birth") val dateOfBirth: LocalDate,
    @SerialName("country") val country: String,
)
