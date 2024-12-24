package io.pc7.ninu.presentation.perfumeSelection


import io.pc7.ninu.data.network.error.DataError
import io.pc7.ninu.data.network.repository.PerfumeRepository
import io.pc7.ninu.domain.model.perfumeSelection.FeelHowSections
import io.pc7.ninu.domain.model.util.ResultMy
import kotlinx.coroutines.CoroutineScope
import io.pc7.ninu.data.network.model.perfumeSelection.PerfumeSelectionItem


class FeelHowViewModel(
    coroutineScope: CoroutineScope? = null,
    perfumeRepository: PerfumeRepository,
): PerfumeSelectionViewModel(coroutineScope, perfumeRepository) {


    override val headers: List<String> = List(3){ FeelHowSections.entries[it].toString() }



    override suspend fun getPerfumes(): ResultMy<List<List<PerfumeSelectionItem>>, DataError.Network> {
//        return when(val sectionsPerfumes = perfumeRepository.getFeelHowSection()){
//            is ResultMy.Error -> sectionsPerfumes
//            is ResultMy.Success -> {
//                val result = FeelHowSections.entries.map { section ->
//                    when (section) {
//                        FeelHowSections.Fresh -> sectionsPerfumes.data.fresh
//                        FeelHowSections.Sensual -> sectionsPerfumes.data.sensual
//                        FeelHowSections.Inspired -> sectionsPerfumes.data.inspired
//                    }
//                }
//                ResultMy.Success(result)
//            }
//        }
        TODO()
    }
}
