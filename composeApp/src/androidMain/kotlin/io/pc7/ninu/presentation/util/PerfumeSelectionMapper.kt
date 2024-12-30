package io.pc7.ninu.presentation.util

import io.pc7.ninu.R.string as s
import io.pc7.ninu.domain.model.perfumeSelection.FeelHowSections
import io.pc7.ninu.domain.model.perfumeSelection.PerfumeSelectionSealedClass
import io.pc7.ninu.domain.model.perfumeSelection.WhereToSections


fun PerfumeSelectionSealedClass.toStringId(): Int{
    return when(this){
        FeelHowSections.Fresh -> s.fresh
        FeelHowSections.Inspired -> s.inspired
        FeelHowSections.Sensual -> s.sensual
        WhereToSections.Work -> s.work
        WhereToSections.Casual -> s.casual
        WhereToSections.Elegant -> s.elegant
    }
}