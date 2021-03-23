package de.freshspark.resify.controllers.api

import de.freshspark.resify.*
import de.freshspark.resify.models.DateRange
import de.freshspark.resify.logic.generateWorkSlots
import de.freshspark.resify.models.ConfigurationWorkSlot
import de.freshspark.resify.models.ReservationsCalendar
import de.freshspark.resify.repositories.CalendarRepository
import de.freshspark.resify.repositories.WorkSlotRepository
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.NoSuchElementException
import javax.ws.rs.core.Response

@RestController
@RequestMapping("/api/calendars")
class CalendarController(
    private val calendarRepository: CalendarRepository,
    private val authenticationInterceptor: AuthenticationInterceptor,
    private val workSlotRepository: WorkSlotRepository,
) {
    @PostMapping("")
    @CompanyRequired
    fun createCalendar(
        @RequestBody calendar: ReservationsCalendar,
    ): Response {
        val currentUser = authenticationInterceptor.currentUser;
        val company = currentUser.company!!
        if(!company.admins.contains(currentUser) && !(company.owner == currentUser)) {
          throw NoAuthorizationException("user not Authorized to create calendar")
        };
        calendar.creator = currentUser;
        calendar.company = company;

        try {
            return Response.status(201)
                .entity(calendarRepository.saveAndFlush(calendar)).build()
        } catch (e: Exception) {
            throw CalendarRouteDuplicate(calendar.route)
        }
    }

    @GetMapping("/{route}")
    fun getCalendar(@PathVariable route: String): ReservationsCalendar? {
      val company = authenticationInterceptor.currentUser.company!!
      return calendarRepository.findByRouteAndCompany(route, company)
    }

    @GetMapping("")
    fun getCalendarsIntern() =
        calendarRepository.findAllByCompany(authenticationInterceptor.currentUser.company!!)

    @PostMapping("/{route}/config-work-slots")
    fun createConfigWorkSlot(
        @PathVariable route: String,
        @RequestBody configurationWorkSlot: ConfigurationWorkSlot
    ): Response {
        val company = authenticationInterceptor.currentUser.company!!
        val calendar = calendarRepository.findByRouteAndCompany(route, company)
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
        val company = authenticationInterceptor.currentUser.company!!
        val calendar = calendarRepository.findByRouteAndCompany(route, company)?:
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
