package de.freshspark.resify.controllers.api.public

import de.freshspark.resify.repositories.CalendarRepository
import de.freshspark.resify.repositories.UserRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/public/{company}")
class CalendarController(val calendarRepository: CalendarRepository) {
    @GetMapping("")
    fun getCalendars(@PathVariable company: String) =
        calendarRepository.findAllByCompanyName(company)
    
    @GetMapping("/{route}")
    fun getCalendar(@PathVariable route: String, @PathVariable company: String) =
      calendarRepository.findByRouteAndCompanyName(route, company)

    @GetMapping("/{route}/reservations")
    fun getRecommendedReservations(
      @PathVariable company: String,
      @PathVariable calendarRoute: String, 
      @RequestParam date: String) {
          
    }
}

@RestController
@RequestMapping("/public/users")
class UserController(val userRepository: UserRepository) {
    @GetMapping("")
    fun getUsers(@RequestParam("q") subString: String) =
        userRepository.findTopByEmailContains(subString)
}
