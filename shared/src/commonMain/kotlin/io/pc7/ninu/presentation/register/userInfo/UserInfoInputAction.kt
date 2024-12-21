package io.pc7.ninu.presentation.register.userInfo

import kotlinx.datetime.LocalDate

sealed class UserInfoInputAction {
    data class OnUsernameChange(val username: String): UserInfoInputAction()
    data class OnDateOfBirthChange(val date: LocalDate?): UserInfoInputAction()
    data class OnUploadProfileImage(val image: String): UserInfoInputAction()
    data object UploadData: UserInfoInputAction()
}