package de.freshspark.resify.controllers.api

import de.freshspark.resify.models.*
import de.freshspark.resify.DataIntegrityViolationException
import de.freshspark.resify.SecurityInterceptor
import de.freshspark.resify.logic.checkIfTimeRangeInWorkSlot
import de.freshspark.resify.repositories.CalendarRepository
import de.freshspark.resify.repositories.ReservationRepository
import de.freshspark.resify.repositories.WorkSlotRepository
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import kotlin.NoSuchElementException

@RestController
@RequestMapping("/api/calendars/{calendarRoute}/reservations")
class ReservationsController(
    val reservationsRepository: ReservationRepository,
    val calendarRepository: CalendarRepository,
    val workSlotRepository: WorkSlotRepository,
    val securityInterceptor: SecurityInterceptor
) {
    @GetMapping()
    fun getReservations(
        @RequestParam day: String,
        @PathVariable calendarRoute: String
    ): Iterable<Reservation> {
        val company = securityInterceptor.currentUser.company!!
        val calendar = calendarRepository.findByRouteAndCompany(calendarRoute, company )
            ?: throw NoSuchElementException("calendar route")
        val workSlots = workSlotRepository.findByCalendarAndDay(calendar, LocalDate.parse(day))
        return workSlots.flatMap { it.reservations }
    }

    @PostMapping
    fun createReservation(
        @RequestBody reservation: Reservation,
        @PathVariable calendarRoute: String
    ): Reservation {
        val company = securityInterceptor.company
        val calendar = calendarRepository.findByRouteAndCompany(calendarRoute, company)
            ?: throw NoSuchElementException("calendar");
        val workSlots =
            workSlotRepository.findByCalendarAndDay(calendar, reservation.day!!)
        val validWorkSlots =
            workSlots.filter { workSlot ->
                checkIfTimeRangeInWorkSlot(
                    workSlot,
                    reservation.timeRange!!
                )
            }
        if (validWorkSlots.isEmpty())
            throw DataIntegrityViolationException("no work slot for reservation")
        if (validWorkSlots.size > 1)
            throw DataIntegrityViolationException("multiple work slots for reservation")
        reservationsRepository.save(reservation)
        validWorkSlots[0].reservations.add(reservation)
        workSlotRepository.save(validWorkSlots[0])
        return reservation
    }
}