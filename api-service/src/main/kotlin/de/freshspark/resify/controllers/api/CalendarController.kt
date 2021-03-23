package de.freshspark.resify.controllers.api

import de.freshspark.resify.*
import de.freshspark.resify.logic.generateWorkSlots
import de.freshspark.resify.logic.hasPermission
import de.freshspark.resify.models.*
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
  private val workSlotRepository: WorkSlotRepository
) {
  val company = authenticationInterceptor.currentUser.company!!
  val currentUser = authenticationInterceptor.currentUser

  val isAdminOrOwner =
    (!company.admins.contains(currentUser) && !(company.owner == currentUser))

  fun hasResourceReadAuthorization(id: UUID) =
    hasPermission(
      currentUser,
      Permission(id, PermissionScope.All, PermissionType.Read)
    )

  fun hasResourceWriteAuthorization(id: UUID) =
    hasPermission(
      currentUser,
      Permission(id, PermissionScope.All, PermissionType.Write)
    )

  private fun canReadCalendar(calendarId: UUID) {
    if (!(isAdminOrOwner && hasResourceReadAuthorization(calendarId)))
      throw NoAuthorizationException(calendarId.toString())
  }

  private fun canCreateCalendar() {
    if (!isAdminOrOwner)
      throw NoAuthorizationException("cannot create a new calendar")
  }

  private fun canWriteCalendar(calendar: ReservationsCalendar) {
    if (!isAdminOrOwner || !hasResourceWriteAuthorization(calendar.id!!))
      throw NoAuthorizationException("cannot write calendar" + calendar.route)
  }

  @PostMapping("")
  @CompanyRequired
  fun createCalendar(
    @RequestBody calendar: ReservationsCalendar,
  ): Response {
    calendar.creator = currentUser
    calendar.company = company
    canCreateCalendar()
    try {
      return Response.status(201)
        .entity(calendarRepository.saveAndFlush(calendar)).build()
    } catch (e: Exception) {
      throw CalendarRouteDuplicate(calendar.route)
    }
  }

  @GetMapping("/{route}")
  fun getCalendar(@PathVariable route: String): ReservationsCalendar {

    val calendar = calendarRepository.findByRouteAndCompany(route, company)
      ?: throw NoSuchElementException(route)
    canReadCalendar(calendar.id!!)
    return calendar
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

    canWriteCalendar(calendar)

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
  fun createWorkSlots(
    @RequestBody dateRange: DateRange,
    @PathVariable route: String
  )
      : Response {
    val company = authenticationInterceptor.currentUser.company!!
    val calendar = calendarRepository.findByRouteAndCompany(route, company)
      ?: throw NoSuchElementException("calendar not found")
    canWriteCalendar(calendar)
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
