package io.pc7.ninu.presentation.register.register

import io.pc7.ninu.data.network.error.DataError
import io.pc7.ninu.domain.model.EmptyResult
import io.pc7.ninu.domain.model.Resource
import io.pc7.ninu.domain.model.input.MyInput

data class RegistrationState(
    val name: MyInput<String> = MyInput(""),
    val surname: MyInput<String> = MyInput(""),
    val email: MyInput<String> = MyInput(""),
    val password: MyInput<String> = MyInput(""),

    val loginRespond: Resource<EmptyResult<DataError.Network>>? = null,
    val enableButton: Boolean = false
)
