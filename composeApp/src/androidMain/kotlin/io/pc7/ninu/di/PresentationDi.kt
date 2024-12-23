package io.pc7.ninu.di

import io.pc7.ninu.data.network.repository.PerfumeRepository
import io.pc7.ninu.domain.model.perfume.Fragrance
import io.pc7.ninu.presentation.lab.LabMainViewModelAndroid
import io.pc7.ninu.presentation.perfumeMainScreen.PerfumeMainViewModelAndroid
import ninu.other.home.HomeScreenViewModelAndroid
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val presentationDi = module {
    viewModelOf(::HomeScreenViewModelAndroid)
    viewModelOf(::LabMainViewModelAndroid)


    viewModel{ (fragrances: Array<Fragrance>, intensity: Int, ) ->
        val perfumeRepository: PerfumeRepository = get()
        PerfumeMainViewModelAndroid(
            perfumeRepository = perfumeRepository,
            intensity = intensity,
            fragrances = fragrances
        )
    }


}