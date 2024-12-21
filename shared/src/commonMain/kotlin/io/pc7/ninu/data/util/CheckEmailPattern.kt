package io.pc7.ninu.data.util



fun checkEmailPattern(email: String): Boolean{
    val emailRegex = Regex(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    )
    return emailRegex.matches(email)
}