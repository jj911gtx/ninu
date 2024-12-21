package io.pc7.ninu.domain.model.input

fun String.isInputEmpty(errors: MutableList<String>): Boolean{
    return if(this.isEmpty()){
        errors.add("Required")
        true
    }else false
}