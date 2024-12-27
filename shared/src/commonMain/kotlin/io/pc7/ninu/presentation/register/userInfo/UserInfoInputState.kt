package io.pc7.ninu.presentation.register.userInfo

import io.pc7.ninu.data.network.error.DataError
import io.pc7.ninu.domain.model.input.MyInput
import io.pc7.ninu.domain.model.util.EmptyResult
import io.pc7.ninu.domain.model.util.Resource
import kotlinx.datetime.LocalDate


data class UserInfoInputState(
    val username: MyInput<String> = MyInput(""),
    val dateOfBirth: MyInput<LocalDate?> = MyInput(null),
    val profileImage: MyInput<Unit?> = MyInput(null),
    val respond: Resource<EmptyResult<DataError.Network>>? = null
)
