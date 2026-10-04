package com.colkorty.mv.date

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.random.Random

data class Date(
    val day: String,
    val month: String,
    val year: Int
)

fun randomDate (): Date {
    val startDate = LocalDate.of(2025, 6, 8)
    val endDate = LocalDate.now().minusWeeks(1)

    val daysBetween = ChronoUnit.DAYS.between(startDate, endDate).coerceAtLeast(0)
    val randomLocalDate = startDate.plusDays(Random.nextLong(daysBetween + 1))

    return Date(
        day = randomLocalDate.dayOfMonth.toString().padStart(2, '0'),
        month = randomLocalDate.monthValue.toString().padStart(2, '0'),
        year = randomLocalDate.year
    )
}