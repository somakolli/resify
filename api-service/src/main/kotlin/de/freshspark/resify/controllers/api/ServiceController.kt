package com.reservationappservice.Controllers.api

import com.reservationappservice.Models.Service
import de.freshspark.resify.repositories.CalendarRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.NoSuchElementException

@RestController
@RequestMapping("/api/calendars/{calendarRoute}/services")
class ServiceController(
    private val calendarRepository: CalendarRepository
) {
    @PostMapping()
    fun postService(
        @PathVariable calendarRoute: String,
        @RequestBody service: Service
    ): Service {
        val calendar = calendarRepository.findByRoute(calendarRoute)
            ?: throw NoSuchElementException("calendar not found");
        calendar.services?.add(service)
        calendarRepository.save(calendar)
        return service
    }
}