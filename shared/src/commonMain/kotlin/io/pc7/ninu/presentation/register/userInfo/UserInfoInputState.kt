package io.pc7.ninu.presentation.register.userInfo

import io.pc7.ninu.domain.model.input.MyInput
import kotlinx.datetime.LocalDate


data class UserInfoInputState(
    val username: MyInput<String> = MyInput(""),
    val dateOfBirth: MyInput<LocalDate?> = MyInput(null),
    val profileImage: MyInput<Boolean> = MyInput(false)

)
