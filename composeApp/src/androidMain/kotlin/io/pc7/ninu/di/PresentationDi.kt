package io.pc7.ninu.di

import io.pc7.ninu.data.network.model.navigation.NavigatePerfumeMain
import io.pc7.ninu.data.network.repository.PerfumeRepository
import io.pc7.ninu.presentation.favourites.EditFavouritesViewModelAndroid
import io.pc7.ninu.presentation.lab.LabMainViewModelAndroid
import io.pc7.ninu.presentation.perfumeDetails.PerfumeMainViewModelAndroid
import io.pc7.ninu.presentation.perfumeSelection.FeelHowViewModelAndroid
import io.pc7.ninu.presentation.perfumeSelection.WhereToViewModeAndroid
import io.pc7.ninu.presentation.settings.changePasword.ChangePasswordViewModelAndroid
import io.pc7.ninu.presentation.settings.profile.ProfileViewModelAndroid
import io.pc7.ninu.presentation.statistics.PerfumeStatusViewModel
import io.pc7.ninu.presentation.statistics.PerfumeStatusViewModelAndroid
import io.pc7.ninu.presentation.statistics.StatisticsViewModeAndroid
import ninu.other.home.HomeScreenViewModelAndroid
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val presentationDi = module {
    viewModelOf(::HomeScreenViewModelAndroid)
    viewModelOf(::LabMainViewModelAndroid)


    viewModel{ (navigatePerfumeMain: NavigatePerfumeMain) ->
        val perfumeRepository: PerfumeRepository = get()
        PerfumeMainViewModelAndroid(
            perfumeRepository = perfumeRepository,
            navigatePerfumeMain = navigatePerfumeMain
        )
    }



    viewModelOf(::WhereToViewModeAndroid)
    viewModelOf(::FeelHowViewModelAndroid)

    viewModelOf(::ProfileViewModelAndroid)
    viewModelOf(::ChangePasswordViewModelAndroid)
    viewModelOf(::StatisticsViewModeAndroid)
    viewModelOf(::PerfumeStatusViewModelAndroid)
    viewModelOf(::EditFavouritesViewModelAndroid)

}