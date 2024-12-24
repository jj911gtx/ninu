package io.pc7.ninu.presentation.perfumeSelection


import io.pc7.ninu.data.network.error.DataError
import io.pc7.ninu.data.network.repository.PerfumeRepository
import io.pc7.ninu.domain.model.perfumeSelection.WhereToSections
import io.pc7.ninu.domain.model.util.ResultMy
import kotlinx.coroutines.CoroutineScope
import io.pc7.ninu.data.network.model.perfumeSelection.PerfumeSelectionItem


class WhereToViewModel(
    coroutineScope: CoroutineScope? = null,
    perfumeRepository: PerfumeRepository,
): PerfumeSelectionViewModel(coroutineScope, perfumeRepository) {


    override val headers: List<String> = List(3){ WhereToSections.entries[it].toString() }



    override suspend fun getPerfumes(): ResultMy<List<List<PerfumeSelectionItem>>, DataError.Network> {
//        val sectionsPerfumes = perfumeRepository.getWhereToSections()
//        return when(sectionsPerfumes){
//            is ResultMy.Error -> sectionsPerfumes
//            is ResultMy.Success -> {
//                val result = WhereToSections.entries.map { section ->
//                    when (section) {
//                        WhereToSections.Work -> sectionsPerfumes.data.work
//                        WhereToSections.Casual -> sectionsPerfumes.data.casual
//                        WhereToSections.Elegant -> sectionsPerfumes.data.elegant
//                    }
//                }
//                ResultMy.Success(result)
//            }
//        }
        TODO()
    }
}


