package tech.mohamedashraf.presentation.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

fun Double.format(): String = String.format("%.2f", this)

private fun localDateTime(dateTime: Long): LocalDateTime {
    val instant: Instant = Instant.fromEpochMilliseconds(dateTime)
    return instant.toLocalDateTime(TimeZone.currentSystemDefault())
}

fun Long.date(): String {
    val localDateTime = localDateTime(this)
    var hour: String
    var part: String
    if (localDateTime.hour >= 12) {
        hour = if (localDateTime.hour-12 < 10) {
            "0${localDateTime.hour-12}"
        } else {
            "${localDateTime.hour-12}"
        }
        part = "PM"
    } else if (localDateTime.hour == 12) {
        hour = "${localDateTime.hour}"
        part = "PM"
    } else if (localDateTime.hour == 0) {
        hour = "12"
        part = "AM"
    } else {
        hour = if (localDateTime.hour < 10) {
            "0${localDateTime.hour}"
        } else {
            "${localDateTime.hour}"
        }
        part = "AM"
    }
    var minute = "${localDateTime.minute}"
    if (localDateTime.minute < 10) {
        minute = "0${localDateTime.minute}"
    }
    val date = "${if (localDateTime.day < 10) "0${localDateTime.day}" else "${localDateTime.day}"}, ${localDateTime.month.name.substring(0, 3)} ${localDateTime.year}"
    return "$date  $hour:$minute $part"
}

@OptIn(ExperimentalTime::class, FormatStringsInDatetimeFormats::class)
fun Long.mmyyyy(): String {
    val localDateTime = localDateTime(this)
    val formatter = LocalDateTime.Format { byUnicodePattern("MM yyyy") }
    return localDateTime.format(formatter).replace(" ", "")
}

fun Long.monthYear(): String {
    val localDateTime = localDateTime(this)
    return "${localDateTime.month.name} ${localDateTime.year}"
}

fun Long.pastMonth(): Long {
    val localDateTime = localDateTime(this)
    val date = localDateTime.date.minus(1, DateTimeUnit.MONTH)
    return LocalDateTime(date = date, time = localDateTime.time).toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

fun Long.nextMonth(): Long {
    val localDateTime = localDateTime(this)
    val date = localDateTime.date.plus(1, DateTimeUnit.MONTH)
    return LocalDateTime(date = date, time = localDateTime.time).toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

@Composable
fun LazyListState.isScrollingUp(): Boolean {
    val previousIndex = remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    val previousScrollOffset = remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }

    return remember(this) {
        derivedStateOf {
            if (previousIndex.intValue != firstVisibleItemIndex) {
                previousIndex.intValue > firstVisibleItemIndex
            } else {
                previousScrollOffset.intValue >= firstVisibleItemScrollOffset
            }.also {
                previousIndex.intValue = firstVisibleItemIndex
                previousScrollOffset.intValue = firstVisibleItemScrollOffset
            }
        }
    }.value
}