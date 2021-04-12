package de.freshspark.resify.controllers.api

import de.freshspark.resify.DataIntegrityViolationException
import de.freshspark.resify.AuthenticationInterceptor
import de.freshspark.resify.CompanyRequired
import de.freshspark.resify.models.WorkSlot
import de.freshspark.resify.repositories.CalendarRepository
import de.freshspark.resify.repositories.WorkSlotRepository
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import kotlin.NoSuchElementException


@RestController
@RequestMapping("/api/calendars/{calendarRoute}/workslots")
class WorkSlotController(
  private val calendarRepository: CalendarRepository,
  private val workSlotRepository: WorkSlotRepository,
  private val authenticationInterceptor: AuthenticationInterceptor
) {
  @PostMapping()
  @CompanyRequired
  fun createWorkSlot(
    @RequestBody workSlot: WorkSlot,
    @PathVariable calendarRoute: String
  ): WorkSlot? {
    val company = authenticationInterceptor.currentUser.company!!
    val calendar =
      calendarRepository.findByRouteAndCompany(calendarRoute, company)
        ?: throw NoSuchElementException("calendar not found");
    val workSlots = workSlotRepository.findByCalendarAndDay(
      calendar,
      workSlot.day!!
    )
    val conflictingSlots = workSlots.filter { existingSlot ->
      workSlot.timeRange!!.inConflict(existingSlot.timeRange!!)
    }
    if (conflictingSlots.isNotEmpty())
      throw DataIntegrityViolationException("workslot " +
          "range in conflict with existing workslot")
    workSlot.calendar = calendar
    return workSlotRepository.save(workSlot)
  }

  @GetMapping("/{day}")
  @CompanyRequired
  fun getWorkSlots(
    @PathVariable calendarRoute: String,
    @PathVariable day: String
  ): List<WorkSlot> {
    val dayDate = LocalDate.parse(day)
    val company = authenticationInterceptor.currentUser.company!!
    val calendar =
      calendarRepository.findByRouteAndCompany(calendarRoute, company)
        ?: throw NoSuchElementException("calendar not found")
    return workSlotRepository.findByCalendarAndDay(calendar, dayDate)
  }

}

