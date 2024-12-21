package io.pc7.ninu.domain.model.input


data class MyInput<T: Any?>(
    val value: T,
    val errors: List<String> = emptyList(),
    val displayErrors: Boolean = false,
){

    fun update(newValue: T): MyInput<T> {
        return this.copy(
            value = newValue,
//            isEmptyError = false,
            errors = emptyList()
        )
    }


//    fun setInputEmpty(): MyInput<T> {
//        return this.copy(
//            isEmptyError = true
//        )
//    }

    fun setDisplayErrors(): MyInput<T> {
        return this.copy(
            displayErrors = true
        )
    }

    fun setErrors(errors: List<String>): MyInput<T> {
        return this.copy(
            errors = errors
        )
    }



    override fun toString(): String {
        return this.value.toString()
    }
}


