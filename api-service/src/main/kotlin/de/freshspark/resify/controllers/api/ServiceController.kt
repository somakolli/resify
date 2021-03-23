package de.freshspark.resify.controllers.api

import de.freshspark.resify.AuthenticationInterceptor
import de.freshspark.resify.CompanyRequired
import de.freshspark.resify.NoAuthorizationException
import de.freshspark.resify.models.*
import de.freshspark.resify.repositories.CalendarRepository
import org.springframework.web.bind.annotation.*
import kotlin.NoSuchElementException

@RestController
@RequestMapping("/api/calendars/{calendarRoute}/services")
class ServiceController(
  val calendarRepository: CalendarRepository,
  val authenticationInterceptor: AuthenticationInterceptor
) {
  val currentUser = authenticationInterceptor.currentUser
  val company = authenticationInterceptor.currentUser.company!!
  val isAdminOrOwner =
    company.admins.contains(currentUser) || company.owner == currentUser

  fun hasResourceReadPermission(calendar: ReservationsCalendar) =
    currentUser.permissions.contains(ReadPermission(calendar.id))

  fun hasResourceWritePermission(calendar: ReservationsCalendar) =
    currentUser.permissions.contains(WritePermission(calendar.id))

  fun canCreateService(calendar: ReservationsCalendar) {
    if(!isAdminOrOwner || !hasResourceWritePermission(calendar))
      throw NoAuthorizationException("not authorized to create service for this calendar")
  }

  @PostMapping()
  @CompanyRequired
  fun postService(
    @PathVariable calendarRoute: String,
    @RequestBody service: Service
  ): Service {
    val company = authenticationInterceptor.currentUser.company!!
    val calendar =
      calendarRepository.findByRouteAndCompany(calendarRoute, company)
        ?: throw NoSuchElementException("calendar not found");
    canCreateService(calendar)
    calendar.services?.add(service)
    calendarRepository.save(calendar)
    return service
  }
}