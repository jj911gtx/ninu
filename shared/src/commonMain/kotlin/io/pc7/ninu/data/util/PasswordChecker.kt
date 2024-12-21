package io.pc7.ninu.data.util




fun String.checkNewPasswordErrors(): List<String>{
    val errors = mutableListOf<String>()
    if(this.isEmpty()){
        errors.add("Required")
    }else{
        if(this.length < 8){
            errors.add("Enter at lest 8 characters")
        }
    }
    return errors
}