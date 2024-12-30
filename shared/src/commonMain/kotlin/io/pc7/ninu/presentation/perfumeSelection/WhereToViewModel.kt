package io.pc7.ninu.presentation.perfumeSelection


import io.pc7.ninu.data.network.error.DataError
import io.pc7.ninu.data.network.repository.PerfumeRepository
import io.pc7.ninu.domain.model.perfumeSelection.WhereToSections
import io.pc7.ninu.domain.model.util.ResultMy
import kotlinx.coroutines.CoroutineScope
import io.pc7.ninu.data.network.model.perfumeSelection.PerfumeSelectionItem
import io.pc7.ninu.domain.model.perfumeSelection.PerfumeSelectionDisplay
import io.pc7.ninu.domain.model.perfumeSelection.PerfumeSelectionSealedClass
import io.pc7.ninu.domain.model.util.Resource
import kotlinx.coroutines.flow.update


class WhereToViewModel(
    coroutineScope: CoroutineScope? = null,
    perfumeRepository: PerfumeRepository,
): PerfumeSelectionViewModel(coroutineScope, perfumeRepository) {


    override val headers: List<PerfumeSelectionSealedClass> = List(3){ WhereToSections.entries[it] }


    private fun create(name: String, id: Int): PerfumeSelectionDisplay{
        return PerfumeSelectionDisplay(
            name = name, id = id, iconLink = "", fragrances = List(3){ PerfumeSelectionDisplay.Fragrance(enough = true, sku = 1, percentage = 20) }
        )
    }

    init {
        val items = Resource.Result(ResultMy.Success(
            PerfumeSelectionState.PerfumesSelections(
                listOf(
                    listOf(
                        create("Hustle", 1),
                        create("Casual Friday office", 2),
                        create("Tuxedo Junction", 3),
                        create("Business lunch", 4),
                        create("Online meeting", 5),
                        create("Sophisticated depth", 6),
                    ),
                    listOf(
                        create("Nomad Life", 7),
                        create("Drinks with friends", 8),
                        create("Flying to the moon ", 9),
                        create("Incense Jungle", 10),
                        create("A day at the races", 11),
                        create("Beautiful Life", 12),
                    ),
                    listOf(
                        create("Absolute Elegance", 13),
                        create("Meeting with the president", 14),
                        create("Embrace of love", 15),
                        create("Gala night", 16),
                        create("Casual charm", 17),
                        create("Sparkling combination", 18),
                    )
                ))
            )
        )

        _state.update { it.copy(perfumes = items) }
    }

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


