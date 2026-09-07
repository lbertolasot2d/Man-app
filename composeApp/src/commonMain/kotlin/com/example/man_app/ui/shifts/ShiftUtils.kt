package com.example.man_app.ui.shifts

import com.example.man_app.data.model.ShiftConfiguration
import kotlinx.datetime.*

object ShiftUtils {
    fun suggestShift(shifts: List<ShiftConfiguration>, currentTimeMillis: Long): String {
        if (shifts.isEmpty()) return "1" // Default fallback

        val instant = Instant.fromEpochMilliseconds(currentTimeMillis)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        
        val dayOfWeek = localDateTime.dayOfWeek
        val minutesFromMidnight = localDateTime.hour * 60 + localDateTime.minute

        // Find a shift that covers the current time
        val currentShift = shifts.find { shift ->
            val (start, end) = getTimesForDay(shift, dayOfWeek)
            if (start != null && end != null) {
                if (start < end) {
                    minutesFromMidnight in start until end
                } else {
                    // Over midnight shift
                    minutesFromMidnight >= start || minutesFromMidnight < end
                }
            } else false
        }

        if (currentShift != null) return currentShift.name

        // If no shift is active, find the next one starting soonest
        // For simplicity, return the first one found or "1"
        return shifts.firstOrNull()?.name ?: "1"
    }

    private fun getTimesForDay(shift: ShiftConfiguration, dayOfWeek: DayOfWeek): Pair<Int?, Int?> {
        return when (dayOfWeek) {
            DayOfWeek.MONDAY -> shift.mondayStart to shift.mondayEnd
            DayOfWeek.TUESDAY -> shift.tuesdayStart to shift.tuesdayEnd
            DayOfWeek.WEDNESDAY -> shift.wednesdayStart to shift.wednesdayEnd
            DayOfWeek.THURSDAY -> shift.thursdayStart to shift.thursdayEnd
            DayOfWeek.FRIDAY -> shift.fridayStart to shift.fridayEnd
            DayOfWeek.SATURDAY -> shift.saturdayStart to shift.saturdayEnd
            DayOfWeek.SUNDAY -> shift.sundayStart to shift.sundayEnd
            else -> null to null
        }
    }
}
