package io.pc7.ninu.domain.usecase

class ScalePercentagesToVolume {

    operator fun invoke(values: List<Int>, target: Int = 2): List<Int> {

        val exactScaledValues = values.map { it * target / 100.toDouble() }

        val integerParts = exactScaledValues.map { it.toInt() }
        val fractionalParts = exactScaledValues.map { it - it.toInt() }

        val integerSum = integerParts.sum()
        val remainder = target - integerSum

        val indicesByFraction = fractionalParts
            .withIndex()
            .sortedByDescending { it.value }
            .map { it.index }

        val result = integerParts.toMutableList()
        for (i in 0 until remainder) {
            val index = indicesByFraction[i]
            result[index] += 1
        }
        return result
    }
}