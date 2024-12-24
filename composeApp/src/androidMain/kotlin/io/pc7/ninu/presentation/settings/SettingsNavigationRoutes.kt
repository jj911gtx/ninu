package io.pc7.ninu.presentation.settings

import kotlinx.serialization.Serializable

object SettingsNavigationRoutes {
    @Serializable
    data object Main
    @Serializable
    data object AboutMe
    @Serializable
    data object FollowNINU

    @Serializable
    data object Language
    @Serializable
    data object ChangePassword
    @Serializable
    data object LegalRegulatory
}