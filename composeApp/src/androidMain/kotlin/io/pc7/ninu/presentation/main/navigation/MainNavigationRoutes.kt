package io.pc7.ninu.presentation.main.navigation

import io.pc7.ninu.domain.model.perfume.Fragrance
import kotlinx.serialization.Serializable

object MainNavigationRoutes {


    @Serializable
    data object Loading

    @Serializable
    data object HomeScreen

    @Serializable
    data object Onboarding

    @Serializable
    data object WhereTo
    @Serializable
    data object FeelHow





    @Serializable
    data object Lab

//    @Serializable
//    data object Profile
//
//    @Serializable
//    data object FAQ
//    @Serializable
//    data object Follow
//    @Serializable
//    data object Language
//
//    @Serializable
//    data object Statistics


    @Serializable
    data class PerfumeSave(
        val fragrances: Array<Fragrance>,
    )

    @Serializable
    data class PerfumeDetails(
        val fragrances: Array<Fragrance>,
        val id: Int,
        val name: String,
    )

}