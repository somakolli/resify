package de.freshspark.resify.controllers.api

import de.freshspark.resify.SecurityInterceptor
import de.freshspark.resify.models.Service
import de.freshspark.resify.repositories.CalendarRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.NoSuchElementException

@RestController
@RequestMapping("/api/calendars/{calendarRoute}/services")
class ServiceController(
    val calendarRepository: CalendarRepository,
    val securityInterceptor: SecurityInterceptor
) {
    @PostMapping()
    fun postService(
        @PathVariable calendarRoute: String,
        @RequestBody service: Service
    ): Service {
        val company = securityInterceptor.company
        val calendar = calendarRepository.findByRouteAndCompany(calendarRoute, company)
            ?: throw NoSuchElementException("calendar not found");
        calendar.services?.add(service)
        calendarRepository.save(calendar)
        return service
    }
}