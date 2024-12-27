package com.fraggeil.ticketator.core.domain

import com.fraggeil.ticketator.core.presentation.StringItem
import com.fraggeil.ticketator.core.presentation.Strings
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object DateTimeUtil {
    val HOLIDAYS = listOf(
        LocalDateTime(2024, 12, 31, 0, 1),
//        LocalDateTime(2024, 11, 1, 0, 1),
//        LocalDateTime(2024, 11, 9, 0, 1),
//        LocalDateTime(2024, 11, 10, 0, 1),
//        LocalDateTime(2024, 11, 15, 0, 1),
    )
    private fun holidaysCountInInterval(from: Long, to: Long): Int{
        val fromDate = Instant.fromEpochMilliseconds(from).toLocalDateTime(TimeZone.currentSystemDefault()).date.atTime(0, 0, 1)
        val toDate = Instant.fromEpochMilliseconds(to).toLocalDateTime(TimeZone.currentSystemDefault()).date.atTime(23, 59, 59)

        val holidaysWithoutSundays = HOLIDAYS.filter { it.date.dayOfWeek != DayOfWeek.SUNDAY }
        return holidaysWithoutSundays.count {
            it.date in fromDate.date..toDate.date
        }
    }

    fun now(): LocalDateTime{
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun nowMilliseconds(): Long{
        return Clock.System.now().toEpochMilliseconds()
    }


    fun toEpochMilli(dateTime: LocalDateTime): Long{
        return dateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }

    fun formatNoteDate(dateTime: LocalDateTime): String{
        val month = dateTime.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() }
        val day = if (dateTime.dayOfMonth < 10) "0${dateTime.dayOfMonth}" else dateTime.dayOfMonth
        val year = dateTime.year
        val hour = if (dateTime.hour < 10) "0${dateTime.hour}" else dateTime.hour
        val minute = if (dateTime.minute < 10) "0${dateTime.minute}" else dateTime.minute

        return buildString {
            append(month)
            append(" ")
            append(day)
            append(" ")
            append(year)
            append(", ")
            append(hour)
            append(":")
            append(minute)
        }
    }

    fun Long.convertUtcToLocalMillis(): Long { // + 5 soat, chunki 3 -iyul 12:00 GTM+5 -> 2-iyul 7PM ga o'tadi
        val instant = Instant.fromEpochMilliseconds(this)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return localDateTime.toInstant(TimeZone.UTC).toEpochMilliseconds()
    }

    fun formatNoteDate(milliseconds: Long): String{
         val dateTime = Instant.fromEpochMilliseconds(milliseconds).toLocalDateTime(TimeZone.currentSystemDefault())
        val month = dateTime.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() }
        val day = if (dateTime.dayOfMonth < 10) "0${dateTime.dayOfMonth}" else dateTime.dayOfMonth
        val year = dateTime.year
        val hour = if (dateTime.hour < 10) "0${dateTime.hour}" else dateTime.hour
        val minute = if (dateTime.minute < 10) "0${dateTime.minute}" else dateTime.minute

        return buildString {
            append(month)
            append(" ")
            append(day)
            append(" ")
            append(year)
            append(", ")
            append(hour)
            append(":")
            append(minute)
        }
    }
    fun getToday(): Pair<Long, Long> {
        val now = nowMilliseconds()
        val start = now.setFromTimeToBeginningOfTheDay()?: now
        return Pair(start, now)
    }

    fun getYesterday(): Pair<Long, Long> {
        val now = nowMilliseconds()
        val fromDate = (now - 24*60*60*1000).setFromTimeToBeginningOfTheDay()
        val toDate = fromDate.setToTimeToEndingOfTheDay()
        return Pair(fromDate!!, toDate!!)
    }

    fun getLast3days(): Pair<Long, Long> {
        val now = nowMilliseconds()
        val fromDate = now.setFromTimeToBeginningOfTheDay()!! - 2*24*60*60*1000
        return Pair(fromDate, now)
    }

    fun getThisWeek(): Pair<Long, Long> {
        val now = nowMilliseconds()
        val dayOfWeek = now().dayOfWeek.ordinal
        val startOfWeekMillis = (now - dayOfWeek*24*60*60*1000).setFromTimeToBeginningOfTheDay()
        return Pair(startOfWeekMillis!!, now)
    }

    fun getLastWeek(): Pair<Long, Long> {
        val now = nowMilliseconds()
        val dayOfWeek = now().dayOfWeek.ordinal
        val startOfWeekMillis = (now - (dayOfWeek+7)*24*60*60*1000).setFromTimeToBeginningOfTheDay()!!
        val endOfWeekMillis = (startOfWeekMillis + 6*24*60*60*1000).setToTimeToEndingOfTheDay()!!
        return Pair(startOfWeekMillis, endOfWeekMillis)
    }

    fun getLast15days(): Pair<Long, Long> {
        val now = nowMilliseconds()
        val fromDate = now.setFromTimeToBeginningOfTheDay()!! - 14*24*60*60*1000
        return Pair(fromDate, now)
    }

    fun getThisMonth(): Pair<Long, Long> {
        val now = nowMilliseconds()
        val dayOfMonth = now().dayOfMonth
        val fromDate = (now - (dayOfMonth-1)*24*60*60*1000).setFromTimeToBeginningOfTheDay()!!

        return Pair(fromDate, now)
    }

    fun getLastMonth(): Pair<Long, Long> {
        val now = nowMilliseconds()

        val dayOfMonth = now().dayOfMonth
        val startOfThisMonth = (now - (dayOfMonth-1)*24*60*60*1000).setFromTimeToBeginningOfTheDay()!!

        val startOfLastMonth = Instant.fromEpochMilliseconds(startOfThisMonth).toLocalDateTime(TimeZone.currentSystemDefault()).date.minus(1, DateTimeUnit.MONTH)
        val startOfLastMonthMillis = startOfLastMonth.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()

        val endOfLastMonthMillis = (startOfThisMonth - 24*60*60*1000).setToTimeToEndingOfTheDay()!!

        return Pair(startOfLastMonthMillis, endOfLastMonthMillis)
    }

    fun getEndingDateIncludingSundaysAndHolidays(startDateInMilliseconds: Long, workDays: Int, isStartDateAlsoIncluded: Boolean = true): Long {
        val startDate = Instant.fromEpochMilliseconds(startDateInMilliseconds).toLocalDateTime(TimeZone.currentSystemDefault())
        var resultDate = startDate.date.minus(if (isStartDateAlsoIncluded) 1 else 0, DateTimeUnit.DAY)
        var workDaysRemaining = workDays

        while (workDaysRemaining != 0){
            resultDate = resultDate.plus(1, DateTimeUnit.DAY)
            if (resultDate.dayOfWeek != DayOfWeek.SUNDAY && !HOLIDAYS.contains(resultDate.atTime(0, 1, 0))) {
                workDaysRemaining--
            }
        }

        val endingDate = resultDate.atTime(23, 59, 59).toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
        return endingDate
    }
    fun getEndingDateIncludingSunday(startDate: Long, workDays: Int): Long{
        val localDateTime = Instant.fromEpochMilliseconds(startDate).toLocalDateTime(TimeZone.currentSystemDefault())
        val startingDayOfWeek = localDateTime.dayOfWeek.ordinal
        val daysUntilFirstSunday = DayOfWeek.SUNDAY.ordinal - startingDayOfWeek
        val remainingDaysWithoutFirstWeek = workDays -daysUntilFirstSunday

        val calendarDays =  if (remainingDaysWithoutFirstWeek > 0){
            remainingDaysWithoutFirstWeek/6*7 + remainingDaysWithoutFirstWeek %6 + daysUntilFirstSunday
        }else{
            workDays-1
        }

        val endingDate = localDateTime.date.plus(calendarDays, DateTimeUnit.DAY).atTime(23, 59, 59).toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
        val holidays = holidaysCountInInterval(startDate, endingDate)
        val endingDateIncludingHolidays = localDateTime.date.plus(calendarDays+holidays, DateTimeUnit.DAY).atTime(23, 59, 59).toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
        return endingDateIncludingHolidays




//        val localDateTime = Instant.fromEpochMilliseconds(startDate).toLocalDateTime(TimeZone.currentSystemDefault())
//        val dayOfWeek = localDateTime.dayOfWeek
//        val daysUntilSunday = (DayOfWeek.SUNDAY.ordinal - dayOfWeek.ordinal + 7) % 7
//        val endingDate = localDateTime.date.plus(daysUntilSunday+days, DateTimeUnit.DAY)
//        // set 23:59:59 to the end of the day
//        return endingDate.atTime(23, 59, 59).toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
//        return endingDate.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }
}

enum class FormattedDateStyle{
    Digital, // 26.11.1973 16:08
    Words, // Thu, 28 May 2024 at 16:08
}
fun Long.toFormattedDate(hoursEnabled: Boolean = false, style: FormattedDateStyle = FormattedDateStyle.Digital):String{
    val dateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
    val month = if (dateTime.monthNumber <10) "0${dateTime.monthNumber}" else dateTime.monthNumber
    val day = if (dateTime.dayOfMonth < 10) "0${dateTime.dayOfMonth}" else dateTime.dayOfMonth
    val year = dateTime.year
    val hour = if (dateTime.hour < 10) "0${dateTime.hour}" else dateTime.hour
    val minute = if (dateTime.minute < 10) "0${dateTime.minute}" else dateTime.minute

    val dayOfWeek = dateTime.dayOfWeek.name.uppercase()

    return when(style){
        FormattedDateStyle.Digital -> if (hoursEnabled) "$day.$month.$year $hour:$minute" else "$day.$month.$year"
        FormattedDateStyle.Words -> if (hoursEnabled) "$dayOfWeek, $day ${dateTime.month.name} $year" else "$dayOfWeek, $day ${dateTime.month.name} $year $hour:$minute"
    }
}

fun Long.getMonthNameAndYearByDate(): Pair<StringItem, Int> {
    val c = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
    val month = c.monthNumber
    val year = c.year


//    val calendar = Calendar.getInstance()
//    calendar.timeInMillis = this
//    val month = calendar.get(Calendar.MONTH)
//    val year = calendar.get(Calendar.YEAR)
    val monthName = when (month) {
        1 -> Strings.January
        2 -> Strings.February
        3 -> Strings.March
        4 -> Strings.April
        5 -> Strings.May
        6 -> Strings.June
        7 -> Strings.July
        8 -> Strings.August
        9 -> Strings.September
        10 -> Strings.October
        11 -> Strings.November
        12 -> Strings.December
        else -> Strings.Unknown
    }
    return Pair(monthName, year)
}
fun Long.getMonthAndYearByDate(): Pair<Int, Int> {
    val c = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
    val month = c.monthNumber
    val year = c.year

    return Pair(month, year)
}

fun getMilliseconds(month: Int, year: Int): Long {
    // Create a LocalDateTime for the first day of the given month and year
    val localDateTime = LocalDateTime(year, month, 1, 0, 0)

    // Convert LocalDateTime to an Instant in UTC
    val instant = localDateTime.toInstant(TimeZone.UTC)

    // Get the milliseconds from the Instant
    return instant.toEpochMilliseconds()
}

fun Long.getMonthBeginningAndEndingByTime():Pair<Long, Long>{
    val c = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
    var month = c.monthNumber
    var year = c.year
    val currentMonthBeginning = LocalDateTime(year, month, 1, 0, 0, 1).toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()

    if (month == 12){
        year += 1
        month = 0
    }
    val currentMonthEnd = LocalDateTime(year, monthNumber = month+1,  1, 0, 0, 0).toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds() - 2
    return Pair(currentMonthBeginning, currentMonthEnd)
}


fun Long?.setFromTimeToBeginningOfTheDay() = if (this != null){
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val startOfDay = localDateTime.date.atTime(0, 0, 1)
    val startOfDayMillis = startOfDay.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    startOfDayMillis
} else null

fun Long?.setToTimeToEndingOfTheDay() = if (this != null){
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val endOfDay = localDateTime.date.atTime(23, 59, 59)
    val endOfDayMillis = endOfDay.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    endOfDayMillis
} else null





