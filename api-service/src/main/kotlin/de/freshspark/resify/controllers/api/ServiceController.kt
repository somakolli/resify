package de.freshspark.resify.controllers.api

import de.freshspark.resify.AuthenticationInterceptor
import de.freshspark.resify.CompanyRequired
import de.freshspark.resify.models.Service
import de.freshspark.resify.repositories.CalendarRepository
import org.springframework.web.bind.annotation.*
import kotlin.NoSuchElementException

@RestController
@RequestMapping("/api/calendars/{calendarRoute}/services")
class ServiceController(
    val calendarRepository: CalendarRepository,
    val authenticationInterceptor: AuthenticationInterceptor
) {
    @PostMapping()
    @CompanyRequired
    fun postService(
        @PathVariable calendarRoute: String,
        @RequestBody service: Service
    ): Service {
        val company = authenticationInterceptor.currentUser.company!!
        val calendar = calendarRepository.findByRouteAndCompany(calendarRoute, company)
            ?: throw NoSuchElementException("calendar not found");
        calendar.services?.add(service)
        calendarRepository.save(calendar)
        return service
    }
}