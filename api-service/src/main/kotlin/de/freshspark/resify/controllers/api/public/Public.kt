package de.freshspark.resify.controllers.api.public

import de.freshspark.resify.logic.calRecommendedTimeRanges
import de.freshspark.resify.models.TimeRange
import de.freshspark.resify.models.WorkSlot
import de.freshspark.resify.repositories.CalendarRepository
import de.freshspark.resify.repositories.CompanyRepository
import de.freshspark.resify.repositories.UserRepository
import de.freshspark.resify.repositories.WorkSlotRepository
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/public/{company}")
class CalendarController(
  val calendarRepository: CalendarRepository,
  val workSlotRepository: WorkSlotRepository
) {
  @GetMapping("")
  fun getCalendars(@PathVariable company: String) =
    calendarRepository.findAllByCompanyName(company)

  @GetMapping("/{route}")
  fun getCalendar(@PathVariable route: String, @PathVariable company: String) =
    calendarRepository.findByRouteAndCompanyName(route, company)

  @GetMapping("/{route}/reservations")
  fun getRecommendedReservations(
    @PathVariable company: String,
    @PathVariable route: String,
    @RequestParam dateString: String,
    @RequestParam length: Long
  ): List<TimeRange> {
    val calendar = calendarRepository.findByRouteAndCompanyName(route, company)
      ?: throw NoSuchElementException("calendar not found")
    val date = LocalDate.parse(dateString)
    val workSlots = workSlotRepository.findByCalendarAndDay(calendar, date)
    val recommendedTimeRanges = mutableListOf<TimeRange>()
    workSlots.forEach { workSlot: WorkSlot ->
      recommendedTimeRanges.addAll(calRecommendedTimeRanges(
        workSlot,
        length,
        15
      ))
    }
    return recommendedTimeRanges
  }
}

@RestController
@RequestMapping("/public")
class PublicController(val companyRepository: CompanyRepository) {
  @GetMapping("/companies")
  fun searchCompanies(@RequestParam("q") searchString: String) =
    companyRepository.findAllByNameContains(searchString)
}
