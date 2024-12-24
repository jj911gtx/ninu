package io.pc7.ninu.domain.model.perfumeSelection

enum class WhereToSections {
    Work,
    Casual,
    Elegant;


    override fun toString(): String {
        return when(this){
            Work -> "work"
            Casual -> "casual"
            Elegant -> "elegant"
        }
    }

    fun toRequest(): String{
        return ""
    }
}