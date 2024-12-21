package io.pc7.ninu.data.mapper

import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.*


fun LocalDate.toTextString(): String{

    return "${month.tolocalizedString().capitalize()} $dayOfMonth $year"
}

fun Month.tolocalizedString(): String{
    return when(this){
        Month.JANUARY -> "january"
        Month.FEBRUARY -> "february"
        Month.MARCH -> "march"
        Month.APRIL -> "april"
        Month.MAY -> "may"
        Month.JUNE -> "june"
        Month.JULY -> "july"
        Month.AUGUST -> "august"
        Month.SEPTEMBER -> "september"
        Month.OCTOBER -> "october"
        Month.NOVEMBER -> "november"
        Month.DECEMBER -> "december"
        else -> ""
    }
}



fun LocalDate.toEpochMillis(): Long{
    return this.toEpochDays() * 86_400_000L
}

fun LocalDate.getMonthDays(): Int{

    val endOfMonth = LocalDate(year, month, 1).plus(DatePeriod(months = 1)).minus(DatePeriod(days = 1))
    return endOfMonth.dayOfMonth

}



fun LocalTime.toTimeString(): String{
    return "$hour:$minute"
}

fun LocalDate.toStringSlash(): String{
    val year4 = year.toString()
    val year2 = year4.substring(2..<year.toString().length)
    return "$dayOfMonth/${month.ordinal}/$year2"
}