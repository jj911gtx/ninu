package io.pc7.ninu.domain.mapper

import io.pc7.ninu.domain.model.util.Language

fun Language.toDisplayString(): String{
    return when(this){
        Language.English -> "English"
        Language.Slovenia -> "Slovenščina"
    }
}