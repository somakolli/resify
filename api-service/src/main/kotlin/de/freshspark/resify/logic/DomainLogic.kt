package de.freshspark.resify.logic

import de.freshspark.resify.models.*
import org.wildfly.common.Assert
import java.util.*
import kotlin.NoSuchElementException

fun checkIfReservationValid(
  reservations: List<Reservation>,
  reservationCandidate: Reservation
): Boolean {
  if (reservations.isNotEmpty()) {
    val conflictingReservations = reservations.filter { reservation ->
      reservationCandidate.timeRange!!.inConflict(reservation.timeRange!!)
    }
    return conflictingReservations.isEmpty()
  }
  return true
}

fun checkIfTimeRangeInWorkSlot(
  workSlot: WorkSlot,
  timeRange: TimeRange
): Boolean {
  val workSlotTimeRange = workSlot.timeRange ?:
      throw NoSuchElementException("[checkIfTimeRangeInWorkSlot]timeRange not found")
  return (workSlotTimeRange.startTime!!.isBefore(timeRange.startTime)
              || workSlotTimeRange.startTime!! == timeRange.startTime)
          && (workSlotTimeRange.endTime!!.isAfter(timeRange.endTime)
              ||  workSlotTimeRange.endTime == timeRange.endTime)
}

fun checkIfTimeRangeInWorkSlot(workSlots: List<WorkSlot>, timeRange: TimeRange)
    : Int {
  var i = 0
  workSlots.forEach { workSlot ->
    if (checkIfTimeRangeInWorkSlot(workSlot, timeRange)) {
      return ++i
    }
  }
  return -1
}

fun generateWorkSlots(
  configSlots: Collection<ConfigurationWorkSlot>,
  dateRange: DateRange,
  calendar: ReservationsCalendar
)
    : List<WorkSlot> {

  val returnSlots = mutableListOf<WorkSlot>()
  val configSlotMap = HashMap<Byte, MutableList<ConfigurationWorkSlot>>();
  for (configSlot in configSlots) {
    configSlotMap.getOrPut(configSlot.weekDay, { mutableListOf() })
      .add(configSlot)
  }

  // if #slots < 7 periodLength = 7
  // else if #slots < 14 periodLength => 14
  // ...
  val periodLength = ((configSlots.size / 7) + 1) * 7

  var currentDate = dateRange.startDate
  var i: Byte = ((dateRange.startDate!!.dayOfWeek.value - 1) % periodLength)
    .toByte();
  while (currentDate != dateRange.endDate) {
    configSlotMap[i]?.forEach { configSlot ->
      val workSlot = WorkSlot(
        day = currentDate,
        timeRange = configSlot.timeRange,
        calendar = calendar
      )
      returnSlots.add(workSlot)
    }
    i = ((i + 1) % periodLength).toByte()
    currentDate = currentDate!!.plusDays(1);
  }
  return returnSlots
}

fun calRecommendedTimeRanges(
  workSlot: WorkSlot,
  reservationLength: Long,
  jumpTime: Long
): MutableList<TimeRange> {
  val startTime = workSlot.timeRange!!.startTime!!
  val endTime = workSlot.timeRange!!.endTime!!
  val recommendations = mutableListOf<TimeRange>()

  var reservationStartTime = startTime
  var reservationEndTime = startTime.plusMinutes(reservationLength)
  while (reservationEndTime.isBefore(endTime)) {
    val candidateTimeRange =
      TimeRange(reservationStartTime, reservationEndTime)
    val valid = workSlot.reservations.none {
      it.timeRange!!.inConflict(candidateTimeRange)
    }
    if (valid)
      recommendations.add(candidateTimeRange)
    reservationStartTime = reservationStartTime.plusMinutes(jumpTime)
    reservationEndTime = reservationStartTime.plusMinutes(jumpTime)
  }
  return recommendations
}

fun getSortedReservations(workSlot: WorkSlot): List<Reservation> {
  return workSlot.reservations.sorted()
}

// precondition: workSlot reservations are not empty
fun getStartGap(workSlot: WorkSlot): Int {
  Assert.assertTrue(workSlot.reservations.isNotEmpty())
  val workSlotStartSeconds =
    workSlot.timeRange!!.startTime!!.toSecondOfDay()
  val reservationStartSeconds =
    getSortedReservations(workSlot).first().timeRange!!.startTime!!.toSecondOfDay()
  return (reservationStartSeconds - workSlotStartSeconds) / 60
}

// precondition: workSlot reservations are not empty
fun getEndGap(workSlot: WorkSlot): Int {
  Assert.assertTrue(workSlot.reservations.isNotEmpty())
  val workSlotEndSeconds =
    workSlot.timeRange!!.endTime!!.toSecondOfDay()
  val reservationEndSeconds =
    getSortedReservations(workSlot).last().timeRange!!.endTime!!.toSecondOfDay()
  return (workSlotEndSeconds - reservationEndSeconds) / 60
}

// precondition: workSlot reservations are not empty
fun calcLargestGap(workSlot: WorkSlot): Int {
  val sortedReservations = getSortedReservations(workSlot)
  var largestGap = 0
  if (sortedReservations.isEmpty())
    return largestGap

  val startGap = getStartGap(workSlot)
  val endGap = getEndGap(workSlot)

  if(startGap > largestGap )
    largestGap = startGap

  if(endGap > largestGap)
    largestGap = endGap

  for (i in 1 until sortedReservations.size) {
    val currentStartSeconds =
      (sortedReservations[i].timeRange!!.startTime!!.toSecondOfDay())
    val previousEndSeconds =
      (sortedReservations[i - 1].timeRange!!.endTime!!.toSecondOfDay())
    val tempSmallestGapMinutes: Int =
      (currentStartSeconds - previousEndSeconds) / 60
    if (largestGap < tempSmallestGapMinutes)
      largestGap = tempSmallestGapMinutes
  }
  return largestGap
}