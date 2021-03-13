package de.freshspark.resify.controllers.api

import de.freshspark.resify.repositories.CalendarRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public/{company}")
class CalendarController(val calendarRepository: CalendarRepository) {
    @GetMapping("")
    fun getCalendars(@PathVariable company: String) =
        calendarRepository.findAllByResifyUserEmail(company)
}