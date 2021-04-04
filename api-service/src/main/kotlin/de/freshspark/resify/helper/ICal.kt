package de.freshspark.resify.helper

import de.freshspark.resify.models.Reservation
import de.freshspark.resify.models.ReservationsCalendar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.NoSuchElementException

data class ICalEvent(val value: String)
data class ICalCalendar(val value: String)
/*
   Transform linebreaks to meet the spec:
   The iCalendar object is organized into individual lines of text,
   called content lines.  Content lines are delimited by a line break,
   which is a CRLF sequence (CR character followed by LF character).
*/
fun String.addCR() : String {
  val splitStrings = split("\r\n","\n", "\r")
  return splitStrings.joinToString("\r\n")
}

val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'hhmmss")

private fun Reservation.toICalEvent(): ICalEvent {
  val timeRange = timeRange ?:
    throw NoSuchElementException("[toICal] timerange not found in reservation")
  val startTime = timeRange.startTime ?:
    throw NoSuchElementException("[toICal] startTime not found in timerange")
  val endTime = timeRange.endTime ?:
    throw NoSuchElementException("[toICal] endTime not found in timerange")
  val day = day ?:
    throw NoSuchElementException("[toICal] day not found in reservation")
  val id = id?:
    throw NoSuchElementException("[toICal] id not found in reservation")

  val startDateTime = LocalDateTime.of(day, startTime)
  val endDateTime = LocalDateTime.of(day, endTime)

  val iCalEventString = """
  BEGIN:VEVENT
  UID:${id}
  DTSAMP:${startDateTime.format(dateTimeFormatter)}
  DTSTART:${startDateTime.format(dateTimeFormatter)}
  DTEND:${endDateTime.format(dateTimeFormatter)}
  END:VEVENT
  """.trimIndent().addCR()
  return ICalEvent(iCalEventString)
}


private val ReservationsCalendar.iCalCalendarWrapper: String
  get() = """
      BEGIN:VCALENDAR
      PRODID:-//hacksw/handcal//NONSGML v1.0//EN
      VERSION:2.0
      %s
      END:VCALENDAR
    """.trimIndent().addCR()

fun Reservation.toICal(calendar: ReservationsCalendar): ICalCalendar =
  ICalCalendar(calendar.iCalCalendarWrapper.format(toICalEvent().value));