package de.freshspark.resify.logic

import de.freshspark.resify.models.*
import java.util.*

fun checkIfReservationValid(reservations: List<Reservation>,
                            reservationCandidate: Reservation): Boolean {
    if (reservations.isNotEmpty()) {
       val conflictingReservations = reservations.filter{ reservation ->
           reservationCandidate.timeRange!!.inConflict(reservation.timeRange!!) }
        return conflictingReservations.isEmpty()
    }
    return true
}
fun checkIfTimeRangeInWorkSlot(workSlot: WorkSlot, timeRange: TimeRange): Boolean {
    val workSlotTimeRange = workSlot.timeRange;
    return workSlotTimeRange!!.startTime!!.isBefore(timeRange.startTime) &&
            workSlotTimeRange.endTime!!.isAfter(timeRange.endTime)
}
fun checkIfTimeRangeInWorkSlot(workSlots: List<WorkSlot>, timeRange: TimeRange)
    :Int
{
    var i = 0
    workSlots.forEach{ workSlot ->
        if (checkIfTimeRangeInWorkSlot(workSlot, timeRange)){
            return ++i
        }
    }
    return -1
}
fun generateWorkSlots(configSlots: Collection<ConfigurationWorkSlot>,
                      dateRange: DateRange,
                      calendar: ReservationsCalendar
)
: List<WorkSlot> {

    val returnSlots = mutableListOf<WorkSlot>()
    val configSlotMap = HashMap<Byte, MutableList<ConfigurationWorkSlot>>();
    for (configSlot in configSlots) {
        configSlotMap.getOrPut(configSlot.weekDay, { mutableListOf() }).add(configSlot)
    }

    // if #slots < 7 periodLength = 7
    // else if #slots < 14 periodLength => 14
    // ...
    val periodLength = ((configSlots.size / 7) + 1) * 7

    var currentDate = dateRange.startDate
    var i: Byte = ((dateRange.startDate!!.dayOfWeek.value - 1) % periodLength)
        .toByte();
    while (currentDate != dateRange.endDate) {
        configSlotMap[i]?.forEach {
            configSlot -> val workSlot = WorkSlot(day = currentDate,
                                    timeRange = configSlot.timeRange,
                                    calendar = calendar)
            returnSlots.add(workSlot)
        }
        i = ((i + 1) % periodLength).toByte()
        currentDate = currentDate!!.plusDays(1);
    }
    return returnSlots
}
