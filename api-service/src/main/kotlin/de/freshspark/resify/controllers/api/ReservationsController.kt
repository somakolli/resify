package de.freshspark.resify.controllers.api

import de.freshspark.resify.models.*
import de.freshspark.resify.AuthenticationInterceptor
import de.freshspark.resify.CompanyRequired
import de.freshspark.resify.NoAuthorizationException
import de.freshspark.resify.repositories.CalendarRepository
import de.freshspark.resify.repositories.ReservationRepository
import de.freshspark.resify.repositories.WorkSlotRepository
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import javax.ws.rs.core.Response
import kotlin.NoSuchElementException

@RestController
@RequestMapping("/api/calendars/{calendarRoute}/reservations")
class ReservationsController(
  val reservationPersistence: ReservationRepository,
  val calendarRepository: CalendarRepository,
  val workSlotRepository: WorkSlotRepository,
  val authenticationInterceptor: AuthenticationInterceptor
) {
  val currentUser = authenticationInterceptor.currentUser
  val company = authenticationInterceptor.currentUser.company!!

  val isAdminOrOwner =
    company.admins.contains(currentUser) || company.owner == currentUser

  fun hasResourceReadAuthorization(calendar: ReservationsCalendar) =
    authenticationInterceptor.permissions.contains(Permission(calendar.id))


  fun hasResourceWriteAuthorization(calendar: ReservationsCalendar) =
    authenticationInterceptor.permissions.contains(
      Permission(
        calendar.id,
        PermissionScope.All, PermissionType.Write
      )
    )


  fun canCreateReservation(calendar: ReservationsCalendar) {
    if (!isAdminOrOwner && !hasResourceWriteAuthorization(calendar))
      throw NoAuthorizationException("not authorized to create reservation")
  }

  fun canGetReservations(calendar: ReservationsCalendar) {
    if (!isAdminOrOwner && !hasResourceReadAuthorization(calendar))
      throw NoAuthorizationException("not authorized to read calendar")
  }

  @GetMapping()
  @CompanyRequired
  fun getReservations(
    @RequestParam day: String,
    @PathVariable calendarRoute: String
  ): Iterable<Reservation> {
    val company = authenticationInterceptor.currentUser.company!!
    val calendar =
      calendarRepository.findByRouteAndCompany(calendarRoute, company)
        ?: throw NoSuchElementException("calendar route")
    canGetReservations(calendar)
    val workSlots =
      workSlotRepository.findByCalendarAndDay(calendar, LocalDate.parse(day))
    return workSlots.flatMap { it.reservations }
  }

  @PostMapping
  @CompanyRequired
  fun createReservation(
    @RequestBody reservation: Reservation,
    @PathVariable calendarRoute: String
  ): Response {
    val company = authenticationInterceptor.currentUser.company!!
    val calendar =
      calendarRepository.findByRouteAndCompany(calendarRoute, company)
        ?: throw NoSuchElementException("calendar");

    canCreateReservation(calendar)
    val savedReservation = reservationPersistence.saveAndValidate(reservation, calendar)
    return Response.status(201).entity(savedReservation).build()
  }
}