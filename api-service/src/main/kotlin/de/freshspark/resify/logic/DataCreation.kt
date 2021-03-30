package de.freshspark.resify.logic

import de.freshspark.resify.models.TimeRange
import java.time.LocalTime

fun createTimeRange(
  startHours: Int,
  startMinutes: Int,
  endHours: Int,
  endMinutes: Int
): TimeRange {
  val timeRange = TimeRange()
  timeRange.startTime = LocalTime.of(startHours, startMinutes)
  timeRange.endTime = LocalTime.of(endHours, endMinutes)
  return timeRange
}

