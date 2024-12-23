package io.pc7.ninu.data.ble.model

import kotlinx.datetime.LocalDate

data class PerfumeSN(
    val type: String,
    val quantity: Int,
    val date: LocalDate,
    val serial: Int,
    val expirationDate: LocalDate,
    val hmac: String
) {
    fun toFormattedString(): String {
        val dateFormatted = date.toString("yyMM")
        val expirationDateFormatted = expirationDate.toString("yyMM")

        return "${type.padStart(3, '0')}_${quantity.toString().padStart(3, '0')}_${dateFormatted}_${serial.toString().padStart(3, '0')}_${expirationDateFormatted}_$hmac"
    }
}


fun LocalDate.toString(pattern: String): String {
    return buildString {
        if (pattern.contains("yyMM")) {
            append(this@toString.year % 100) // Extract last two digits of the year
            append(this@toString.monthNumber.toString().padStart(2, '0'))
        }
    }
}
