package com.reservationappservice.Controllers.api

import com.reservationappservice.Models.ConfigurationWorkSlot
import com.reservationappservice.Models.ReservationsCalendar
import com.reservationappservice.logic.generateWorkSlots
import de.freshspark.resify.CalendarRouteDuplicate
import de.freshspark.resify.DataIntegrityViolationException
import de.freshspark.resify.Models.DateRange
import de.freshspark.resify.SecurityInterceptor
import de.freshspark.resify.repositories.CalendarRepository
import de.freshspark.resify.repositories.WorkSlotRepository
import org.springframework.web.bind.annotation.*
import kotlin.NoSuchElementException
import org.jboss.logging.Logger
import javax.ws.rs.core.Response

@RestController
@RequestMapping("/api/calendars")
class CalendarController(
    private val calendarRepository: CalendarRepository,
    private val securityInterceptor: SecurityInterceptor,
    private val workSlotRepository: WorkSlotRepository
) {

    val logger = Logger.getLogger(CalendarController::class.java)


    @PostMapping("")
    @Throws(CalendarRouteDuplicate::class)
    fun createCalendar(
        @RequestBody calendar: ReservationsCalendar,
    ): Response {
        calendar.resifyUser = securityInterceptor.currentUser
        logger.info("test")
        try {
            return Response.status(201)
                .entity(calendarRepository.saveAndFlush(calendar)).build()
        } catch (e: Exception) {
            throw CalendarRouteDuplicate(calendar.route)
        }
    }

    @GetMapping("/{route}")
    fun getCalendar(@PathVariable route: String) =
        calendarRepository.findByRoute(route)

    @GetMapping("")
    fun getCalendarsIntern() =
        calendarRepository.findAllByResifyUser(securityInterceptor.currentUser!!)

    @PostMapping("/{route}/config-work-slots")
    fun createConfigWorkSlot(
        @PathVariable route: String,
        @RequestBody configurationWorkSlot: ConfigurationWorkSlot
    ): Response {
        val calendar = calendarRepository.findByRoute(route)
            ?: throw NoSuchElementException("calendar not found")

        // check for conflicts
        for (workSlot in calendar.workSlotConfiguration!!.filter
        { workSlot -> workSlot.weekDay == configurationWorkSlot.weekDay }) {
            if (workSlot.timeRange!!.inConflict(configurationWorkSlot.timeRange!!))
                throw DataIntegrityViolationException(
                    "work slot in conflict with " +
                            "existing workslot"
                )
        }

        // if this is reached: no conflicts found -> ready to add

        calendar.workSlotConfiguration!!.add(configurationWorkSlot)
        calendarRepository.save(calendar);
        return Response.status(201).entity(configurationWorkSlot)
            .build()
    }

    @PostMapping("/{route}/work-slots/generate")
    fun createWorkSlots(@RequestBody dateRange: DateRange,
                        @PathVariable route: String)
    : Response
    {
        val calendar = calendarRepository.findByRoute(route)?:
                throw NoSuchElementException("calendar not found")
        for (generatedWorkSlot in generateWorkSlots(
            calendar.workSlotConfiguration!!,
            dateRange,
            calendar
        )) {
            workSlotRepository.save(generatedWorkSlot)
        }
        return Response.status(201).build()
    }
}