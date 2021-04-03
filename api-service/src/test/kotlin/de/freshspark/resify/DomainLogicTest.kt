package de.freshspark.resify

import de.freshspark.resify.logic.*
import de.freshspark.resify.models.Reservation
import de.freshspark.resify.models.TimeRange
import de.freshspark.resify.models.WorkSlot
import org.junit.jupiter.api.Test
import org.wildfly.common.Assert
import java.time.LocalTime

class DomainLogicTest {
  @Test
  fun timeRangeInConflict() {
    var timeRange1 = TimeRange()
    timeRange1.startTime = LocalTime.of(13, 30)
    timeRange1.endTime = LocalTime.of(14, 0)
    var timeRange2 = TimeRange()
    timeRange2.startTime = (LocalTime.of(14, 0))
    timeRange2.endTime = (LocalTime.of(15, 30))
    var conflict = timeRange1.inConflict(timeRange2)
    Assert.assertFalse(conflict)

    timeRange1 = TimeRange()
    timeRange1.startTime = (LocalTime.of(13, 30))
    timeRange1.endTime = (LocalTime.of(14, 0))
    timeRange2 = TimeRange()
    timeRange2.startTime = (LocalTime.of(13, 45))
    timeRange2.endTime = (LocalTime.of(14, 15))
    conflict = timeRange1.inConflict(timeRange2)
    Assert.assertTrue(conflict)

    timeRange1 = TimeRange()
    timeRange1.startTime = (LocalTime.of(13, 30))
    timeRange1.endTime = (LocalTime.of(14, 0))
    timeRange2 = TimeRange()
    timeRange2.startTime = (LocalTime.of(13, 45))
    timeRange2.endTime = (LocalTime.of(13, 50))
    conflict = timeRange1.inConflict(timeRange2)
    Assert.assertTrue(conflict)

    timeRange1 = TimeRange()
    timeRange1.startTime = (LocalTime.of(13, 45))
    timeRange1.endTime = (LocalTime.of(14, 15))
    timeRange2 = TimeRange()
    timeRange2.startTime = (LocalTime.of(13, 30))
    timeRange2.endTime = (LocalTime.of(14, 0))
    conflict = timeRange1.inConflict(timeRange2)
    Assert.assertTrue(conflict)
  }

  @Test
  fun timeRangeInConflictWithReservations() {
    val reservations = ArrayList<Reservation>()

    val reservation = Reservation()
    val timeRange = TimeRange()
    timeRange.startTime = (LocalTime.of(14, 0))
    timeRange.endTime = (LocalTime.of(14, 30))
    reservation.timeRange = (timeRange)
    Assert.assertTrue(checkIfReservationValid(reservations, reservation))
    reservations.add(reservation)

    val reservation2 = Reservation()
    val timeRange2 = TimeRange()
    timeRange2.startTime = (LocalTime.of(14, 15))
    timeRange2.endTime = (LocalTime.of(14, 35))
    reservation2.timeRange = (timeRange)
    Assert.assertFalse(checkIfReservationValid(reservations, reservation2))
  }

  @Test
  fun timeRangeInWorkSlot() {
    var timeRange = TimeRange()
    timeRange.startTime = (LocalTime.of(13, 0))
    timeRange.endTime = (LocalTime.of(14, 45))
    var workSlot = WorkSlot()
    var workSlotTimeRange = TimeRange()
    workSlotTimeRange.startTime = (LocalTime.of(13, 0))
    workSlotTimeRange.endTime = (LocalTime.of(18, 0))
    workSlot.timeRange = (workSlotTimeRange)

    Assert.assertTrue(checkIfTimeRangeInWorkSlot(workSlot, timeRange))


    timeRange = TimeRange()
    timeRange.startTime = (LocalTime.of(14, 30))
    timeRange.endTime = (LocalTime.of(14, 45))
    workSlot = WorkSlot()
    workSlotTimeRange = TimeRange()
    workSlotTimeRange.startTime = (LocalTime.of(8, 0))
    workSlotTimeRange.endTime = (LocalTime.of(12, 0))
    workSlot.timeRange = (workSlotTimeRange)

    Assert.assertFalse(checkIfTimeRangeInWorkSlot(workSlot, timeRange))

    timeRange = TimeRange()
    timeRange.startTime = (LocalTime.of(11, 30))
    timeRange.endTime = (LocalTime.of(12, 45))
    workSlot = WorkSlot()
    workSlotTimeRange = TimeRange()
    workSlotTimeRange.startTime = (LocalTime.of(8, 0))
    workSlotTimeRange.endTime = (LocalTime.of(12, 0))
    workSlot.timeRange = (workSlotTimeRange)

    Assert.assertFalse(checkIfTimeRangeInWorkSlot(workSlot, timeRange))

    timeRange = TimeRange()
    timeRange.startTime = (LocalTime.of(7, 30))
    timeRange.endTime = (LocalTime.of(8, 30))
    workSlot = WorkSlot()
    workSlotTimeRange = TimeRange()
    workSlotTimeRange.startTime = (LocalTime.of(8, 0))
    workSlotTimeRange.endTime = (LocalTime.of(12, 0))
    workSlot.timeRange = (workSlotTimeRange)

    Assert.assertFalse(checkIfTimeRangeInWorkSlot(workSlot, timeRange))
  }

  @Test
  fun startAndEndGapWorks() {
    val workSlot = WorkSlot()
    workSlot.timeRange = createTimeRange(8, 30, 18, 0)

    val reservations = mutableListOf<Reservation>()
    val reservation1 = Reservation()
    reservation1.timeRange = createTimeRange(9,0, 10,0)
    reservations.add(reservation1)
    workSlot.reservations = reservations
    val startGap = getStartGap(workSlot)
    val endGap = getEndGap(workSlot)
    Assert.assertTrue(startGap == 30)
    Assert.assertTrue(endGap == 8 * 60)
  }

  @Test
  fun calcGapWorks() {
    val workSlot = WorkSlot()
    workSlot.timeRange = createTimeRange(8, 30, 18, 0)

    val reservations = mutableListOf<Reservation>()
    val reservation1 = Reservation()
    reservation1.timeRange = createTimeRange(9,0, 10,0)
    reservations.add(reservation1)
    workSlot.reservations = reservations
    Assert.assertTrue(calcLargestGap(workSlot) == 8 * 60)

    val reservation2 = Reservation()
    reservation2.timeRange = createTimeRange(17, 0, 18, 0)
    workSlot.reservations.add(reservation2)
    Assert.assertTrue(calcLargestGap(workSlot) == 7 * 60)
  }
}