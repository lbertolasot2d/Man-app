package com.example.man_app.util

import kotlinx.datetime.*

fun getStartOfDay(epochMillis: Long): Long {
    val instant = Instant.fromEpochMilliseconds(epochMillis)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return LocalDateTime(localDateTime.year, localDateTime.monthNumber, localDateTime.dayOfMonth, 0, 0)
        .toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

fun getEndOfDay(epochMillis: Long): Long {
    val instant = Instant.fromEpochMilliseconds(epochMillis)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return LocalDateTime(localDateTime.year, localDateTime.monthNumber, localDateTime.dayOfMonth, 23, 59, 59)
        .toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

fun formatDateTime(epochMillis: Long): String {
    val instant = Instant.fromEpochMilliseconds(epochMillis)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.dayOfMonth.toString().padStart(2, '0')}/${localDateTime.monthNumber.toString().padStart(2, '0')}/${localDateTime.year.toString().takeLast(2)} ${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}"
}

fun formatDate(epochMillis: Long): String {
    val instant = Instant.fromEpochMilliseconds(epochMillis)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.dayOfMonth.toString().padStart(2, '0')}/${localDateTime.monthNumber.toString().padStart(2, '0')}/${localDateTime.year.toString().takeLast(2)}"
}

fun formatTime(epochMillis: Long): String {
    val instant = Instant.fromEpochMilliseconds(epochMillis)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}"
}

fun formatInputDate(input: String): String {
    val digits = input.filter { it.isDigit() }
    return when {
        digits.length <= 2 -> digits
        digits.length <= 4 -> "${digits.take(2)}/${digits.substring(2)}"
        else -> "${digits.take(2)}/${digits.substring(2, 4)}/${digits.substring(4, 6)}"
    }
}

fun formatInputTime(input: String): String {
    val digits = input.filter { it.isDigit() }
    return when {
        digits.length <= 2 -> digits
        else -> "${digits.take(2)}:${digits.substring(2, 4)}"
    }
}

fun formatMin(totalMin: Int): String {
    val h = totalMin / 60
    val m = totalMin % 60
    return "${h.toString().padStart(2, '0')}:${m.toString().padStart(2, '0')}"
}
