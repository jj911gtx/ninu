package io.pc7.ninu.presentation.lab.graphs


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import io.pc7.ninu.presentation.lab.screen.PerfumePercentageEditor
import io.pc7.ninu.presentation.lab.screen.UpdateGraphPercentages
import kotlin.math.PI
import kotlin.math.abs


class CircularSliderAdapter(
    private var percentages: List<Int>,
//    selectedPerfumeIndex: Int,
    private val savePercentages: (List<Int>) -> Unit,
){


    val editorData: MutableState<PerfumePercentageEditor?> = mutableStateOf(null/*selectPerfumePercentageEditor()*/)

    fun selectPerfume(selectedPerfumeIndex: Int){
        editorData.value = selectPerfumePercentageEditor(selectedPerfumeIndex)
    }



    private lateinit var nextPerfumePercentagesIndexes: List<Int>

    private fun selectPerfumePercentageEditor(index: Int): PerfumePercentageEditor {
        fun calculateAngles(perfumes: List<Int>): List<PerfumeAngles>{
            val perfumesAngles: MutableList<PerfumeAngles> = mutableListOf()
            var startAngle = 0.0
            var endAngle: Double
            perfumes.forEach {
                endAngle = it / 100f * (2 * PI) + startAngle
                perfumesAngles.add(
                    PerfumeAngles(
                        startAngle.toFloat(),
                        endAngle.toFloat()
                    )
                )
                startAngle = endAngle
            }
            return perfumesAngles
        }
        val editorData: PerfumePercentageEditor = index.let { selectedPerfumeIndex ->
            val perfumesAngles = calculateAngles(percentages)

            val selectedPerfumeAngles = perfumesAngles[selectedPerfumeIndex]

            nextPerfumePercentagesIndexes = getRotatedIndices(selectedPerfumeIndex)
            PerfumePercentageEditor(
                index = selectedPerfumeIndex,
                startAngle = selectedPerfumeAngles.startAngle.toDouble(),
                endAngle = selectedPerfumeAngles.endAngle.toDouble(),
            )
        }
        return editorData
    }

    private fun getRotatedIndices(mainIndex: Int, size: Int = 3): List<Int> {
        val rotatedIndices = mutableListOf<Int>()

        for (i in 1 until size) {
            rotatedIndices.add((mainIndex + i) % size)
        }
        return rotatedIndices
    }



    fun updatePercentages(change: UpdateGraphPercentages) {
        editorData.value?.let { editor ->
            var editor = editor
            val percentages = percentages.toMutableList()

            fun updateAngle(newAngle: Double, isStart: Boolean): Pair<Int, Int> {
                val previousPercentage = calculatePercentageBetweenAngles(editor.startAngle, editor.endAngle)
                var activePercentage = if (isStart)
                    calculatePercentageBetweenAngles(newAngle, editor.endAngle)
                else
                    calculatePercentageBetweenAngles(editor.startAngle, newAngle)

                val difference = previousPercentage - activePercentage
                val percentageDifference = if (abs(difference) > 50) {
                    println("(abs(difference) > 50")
                    if (difference > 50) {
                        println("difference > 50")
                        editor = if (isStart) {
                            editor.copy(startAngle = editor.endAngle + 0.0001f)
                        } else {
                            editor.copy(endAngle = editor.startAngle - 0.0001f)
                        }
                        activePercentage = 100f
                        100 - percentages[editor.index]
                    } else {
                        println("difference > 50 ... else")
                        editor = if (isStart) {
                            println("if (isStart)")
                            editor.copy(startAngle = editor.endAngle - 0.0001f)
                        } else {
                            println("if (isStart) ... else")
                            editor.copy(endAngle = editor.startAngle + 0.0001f)
                        }
                        activePercentage = 0f
                        -percentages[editor.index]
                    }
                } else {
                    println("(abs(difference) > 50 ... else")
                    editor = if (isStart) editor.copy(startAngle = newAngle)
                    else editor.copy(endAngle = newAngle)
                    activePercentage.toInt() - percentages[editor.index]
                }

                return percentageDifference to activePercentage.toInt()
            }

            fun adjustPerfumePercentages(percentageDifference: Int, isStart: Boolean) {
                if (percentageDifference > 0) {
                    var remaining = percentageDifference
                    for (i in if (isStart) nextPerfumePercentagesIndexes.size - 1 downTo 0 else nextPerfumePercentagesIndexes.indices) {
                        val index = nextPerfumePercentagesIndexes[i]
                        val percentage = percentages[index]
                        if (percentage > 0) {
                            var newPercentage = percentage - abs(remaining)
                            remaining = if (newPercentage < 0) newPercentage else 0
                            newPercentage = newPercentage.coerceAtLeast(0)
                            percentages[index] = newPercentage
                            if (remaining >= 0f) break
                        }
                    }
                } else if (percentageDifference < 0) {
                    val index = if (isStart) nextPerfumePercentagesIndexes.last() else nextPerfumePercentagesIndexes.first()
                    percentages[index] = percentages[index] - percentageDifference
                }
            }

            when (change) {
                is UpdateGraphPercentages.Start -> {
                    val (percentageDifference, newPercentage) = updateAngle(change.angle, isStart = true)
                    adjustPerfumePercentages(percentageDifference, isStart = true)
                    percentages[editor.index] = newPercentage
                }
                is UpdateGraphPercentages.End -> {
                    val (percentageDifference, newPercentage) = updateAngle(change.angle, isStart = false)
                    println("$percentageDifference ...  $newPercentage")
                    adjustPerfumePercentages(percentageDifference, isStart = false)
                    percentages[editor.index] = newPercentage
                }
            }

            this.percentages = percentages
            this.editorData.value = editor
            savePercentages(this.percentages)
        }

    }


    private fun calculatePercentageBetweenAngles(startAngle: Double, endAngle: Double): Float {

        val angleDifference = if (endAngle >= startAngle) {
            endAngle - startAngle
        } else {
            (2 * PI) - (startAngle - endAngle)
        }

        return (angleDifference.toFloat() / (2 * PI).toFloat()) * 100
    }


    data class PerfumeAngles(
        var startAngle: Float,
        var endAngle: Float
    )
}