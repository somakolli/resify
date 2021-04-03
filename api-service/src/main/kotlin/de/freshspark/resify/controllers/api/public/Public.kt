package de.freshspark.resify.controllers.api.public

import de.freshspark.resify.logic.calRecommendedTimeRanges
import de.freshspark.resify.models.Reservation
import de.freshspark.resify.models.TimeRange
import de.freshspark.resify.models.WorkSlot
import de.freshspark.resify.repositories.*
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
      recommendedTimeRanges.addAll(
        calRecommendedTimeRanges(
          workSlot,
          length,
          15
        )
      )
    }
    return recommendedTimeRanges
  }

  @GetMapping("/{route}/days")
  fun getAvailableDays(
    @PathVariable company: String,
    @PathVariable route: String,
    @RequestParam dateString: String,
    @RequestParam length: Long
  ) {
    val calendar = calendarRepository.findByRouteAndCompanyName(route, company)
      ?: throw NoSuchElementException("calendar not found")
    val date = LocalDate.parse(dateString)
    val daysInMonth = date.month.length(date.isLeapYear)
    val availableDaySet = mutableSetOf<Int>()

    for(i in 1..daysInMonth) {
      val tempDate = LocalDate.of(date.year, date.monthValue, date.dayOfMonth)
      val workSlots = workSlotRepository.findByCalendarAndDay(calendar, tempDate)
      for(workSlot in workSlots) {
        if(workSlot.largestGap >= length) {
          availableDaySet.add(tempDate.dayOfMonth)
        }
      }
    }
  }
}

@RestController
@RequestMapping("/public")
class CompanyController(val companyRepository: CompanyRepository) {
  @GetMapping("/companies")
  fun searchCompanies(@RequestParam("q") searchString: String) =
    companyRepository.findAllByNameContains(searchString)
}


@RestController
@RequestMapping("/public/{companyName}/{calendarRoute}")
class ReservationController(
  val calendarRepository: CalendarRepository,
  val reservationPersistence: ReservationRepository) {
  @PostMapping("/reservations")
  fun postReservation(
    @RequestBody reservation: Reservation,
    @PathVariable companyName: String,
    @PathVariable calendarRoute: String,
    ): Reservation? {
    val calendar = calendarRepository.findByRouteAndCompanyName(calendarRoute, companyName) ?:
          throw NoSuchElementException("calendar not found")

    return reservationPersistence.saveAndValidate(reservation, calendar)
  }
}
