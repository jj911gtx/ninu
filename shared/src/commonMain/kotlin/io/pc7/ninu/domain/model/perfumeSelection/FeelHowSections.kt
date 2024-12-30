package io.pc7.ninu.domain.model.perfumeSelection

enum class FeelHowSections: PerfumeSelectionSealedClass {
    Fresh,
    Inspired,
    Sensual;

//    override fun toString(): String {
//        return when(this){
//            Fresh -> "fresh"
//            Inspired -> "inspired"
//            Sensual -> "sensual"
//        }
//    }

    fun toRequest(): String{
        return ""
    }
}