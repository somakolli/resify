package de.freshspark.resify.controllers.api

import de.freshspark.resify.repositories.CalendarRepository
import de.freshspark.resify.repositories.UserRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/public/{company}")
class CalendarController(val calendarRepository: CalendarRepository) {
    @GetMapping("")
    fun getCalendars(@PathVariable company: String) =
        calendarRepository.findAllByResifyUserEmail(company)
    
    @GetMapping("/{route}")
    fun getCalendar(@PathVariable route: String) = 
      calendarRepository.findByRoute(route)
}

@RestController
@RequestMapping("/public/users")
class UserController(val userRepository: UserRepository) {
    @GetMapping("")
    fun getUsers(@RequestParam("q") subString: String) =
        userRepository.findTopByEmailContains(subString)
}
