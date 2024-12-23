package io.pc7.ninu.presentation.lab.screen

sealed class LabMainAction {
    /**
     * index of perfume in list
     * */
//    data class OnSelectPerfumePercentageEditor(val index: Int?): LabMainAction
    data class OnUpdateIntensity(val intensity: Int): LabMainAction()
    data class OnSavePercentages(val percentages: List<Int>): LabMainAction()
    data object LoadScent: LabMainAction()

}

sealed class UpdateGraphPercentages {
    data class Start(val angle: Double): UpdateGraphPercentages()
    data class End(val angle: Double): UpdateGraphPercentages()
}