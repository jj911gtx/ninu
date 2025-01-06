package io.pc7.ninu.presentation.register.userInfo

import io.pc7.ninu.domain.model.util.Country
import kotlinx.datetime.LocalDate

sealed class UserInfoInputAction {
    data class OnUsernameChange(val username: String): UserInfoInputAction()
    data class OnDateOfBirthChange(val date: LocalDate?): UserInfoInputAction()
    data class OnUploadProfileImage(val value: ByteArray): UserInfoInputAction()
    data class OnUpdateCountry(val country: Country): UserInfoInputAction()
    data object UploadData: UserInfoInputAction()
}