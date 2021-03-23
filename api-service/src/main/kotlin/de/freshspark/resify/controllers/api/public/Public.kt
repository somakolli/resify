package de.freshspark.resify.controllers.api.public

import de.freshspark.resify.repositories.CalendarRepository
import de.freshspark.resify.repositories.CompanyRepository
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
@RequestMapping("/public")
class PublicController(val companyRepository: CompanyRepository) {
  @GetMapping("/companies")
  fun searchCompanies(@RequestParam("q") searchString: String) =
    companyRepository.findAllByNameContains(searchString)
}
