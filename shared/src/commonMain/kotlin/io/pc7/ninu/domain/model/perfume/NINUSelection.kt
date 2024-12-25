package io.pc7.ninu.domain.model.perfume

enum class NINUSelection {
    N1,
    N2,
    N3,
    N;

    fun toHex(): Long{
        return when(this){
            N1 -> 0xFFF8FFAC
            N2 -> 0xFFFFA2F0
            N3 -> 0xFFA2FFFF
            N -> 0xFFC5837D
        }
    }

    override fun toString(): String {
        return when(this){
            N1 -> "1"
            N2 -> "2"
            N3 -> "3"
            N -> "N"
        }
    }
}