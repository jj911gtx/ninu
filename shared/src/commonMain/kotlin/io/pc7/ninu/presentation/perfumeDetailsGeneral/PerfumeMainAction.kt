package io.pc7.ninu.presentation.perfumeDetailsGeneral

sealed class PerfumeMainAction {
    data class OnUpdateName(val name: String): PerfumeMainAction()
    data class OnSelectIcon(val iconId: Int): PerfumeMainAction()
    data object OnClickFavourite: PerfumeMainAction()
//    data object OnClickEdit: PerfumeMainAction()
    data object OnClickDelete: PerfumeMainAction()
    data object OnClickSave: PerfumeMainAction()

}