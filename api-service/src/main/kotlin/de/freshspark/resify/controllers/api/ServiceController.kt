package de.freshspark.resify.controllers.api

import de.freshspark.resify.AuthenticationInterceptor
import de.freshspark.resify.CompanyRequired
import de.freshspark.resify.NoAuthorizationException
import de.freshspark.resify.models.*
import de.freshspark.resify.repositories.CalendarRepository
import de.freshspark.resify.repositories.ServiceRepository
import org.springframework.web.bind.annotation.*
import javax.ws.rs.core.Response
import kotlin.NoSuchElementException

@RestController
@RequestMapping("/api/calendars/{calendarRoute}/services")
class ServiceController(
  val calendarRepository: CalendarRepository,
  val authenticationInterceptor: AuthenticationInterceptor,
  val serviceRepository: ServiceRepository
) {
  val currentUser = authenticationInterceptor.currentUser
  val company = authenticationInterceptor.currentUser.company!!
  val isAdminOrOwner =
    company.admins.contains(currentUser) || company.owner == currentUser

  fun hasResourceReadPermission(calendar: ReservationsCalendar) =
    currentUser.permissions.contains(readPermission(calendar.id))

  fun hasResourceWritePermission(calendar: ReservationsCalendar) =
    currentUser.permissions.contains(writePermission(calendar.id))

  fun canCreateService(calendar: ReservationsCalendar) {
    if(!isAdminOrOwner && !hasResourceWritePermission(calendar))
      throw NoAuthorizationException("not authorized to create service for this calendar")
  }

  @PostMapping()
  @CompanyRequired
  fun postService(
    @PathVariable calendarRoute: String,
    @RequestBody service: Service
  ): Response {
    val company = authenticationInterceptor.currentUser.company!!
    val calendar =
      calendarRepository.findByRouteAndCompany(calendarRoute, company)
        ?: throw NoSuchElementException("calendar not found");
    canCreateService(calendar)
    val createdService = serviceRepository.save(service)
    calendar.services?.add(createdService)
    calendarRepository.save(calendar)
    return Response.status(201).entity(createdService).build()
  }
}