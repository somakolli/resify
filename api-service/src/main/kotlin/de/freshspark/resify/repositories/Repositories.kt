package de.freshspark.resify.repositories

import de.freshspark.resify.DataIntegrityViolationException
import de.freshspark.resify.logic.calcLargestGap
import de.freshspark.resify.logic.checkIfTimeRangeInWorkSlot
import de.freshspark.resify.models.*
import org.everit.json.schema.loader.SchemaLoader
import org.json.JSONObject
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.ext.Provider

interface UserRepository : JpaRepository<ResifyUser, UUID> {
  fun findByEmailAndCompany(email: String, company: Company): ResifyUser?
  fun findAllByCompany(company: Company): List<ResifyUser>?
  fun findByEmail(email: String): ResifyUser?
}

interface CalendarRepository : JpaRepository<ReservationsCalendar, UUID> {
  fun findByRouteAndCompany(
    route: String,
    company: Company
  ): ReservationsCalendar?

  fun findByRouteAndCompanyName(
    route: String,
    companyName: String
  ): ReservationsCalendar?

  fun findAllByCompany(company: Company): List<ReservationsCalendar>?
  fun findAllByCompanyName(companyName: String): List<ReservationsCalendar>?
}

interface ServiceRepository : JpaRepository<Service, UUID> {

}

interface WorkSlotRepository : JpaRepository<WorkSlot, UUID> {
  fun findByCalendarAndDay(
    calendar: ReservationsCalendar,
    day: LocalDate
  ): List<WorkSlot>
}

interface ConfigurationWorkSlotsRepository :
  JpaRepository<ConfigurationWorkSlot, UUID> {
}

interface ReservationRepository : JpaRepository<Reservation, UUID>,
  ReservationPersistenceInterface {
    // dont use the JpaRepository save function outside of this class
}

interface CompanyRepository : JpaRepository<Company, UUID> {
  fun findAllByNameContains(searchString: String): List<Company>?
}

interface ReservationPersistenceInterface {
  // use this save function for creating reservations
  fun saveAndValidate(
    reservation: Reservation,
    calendar: ReservationsCalendar
  ): Reservation?
}


private class ReservationPersistence(
  val reservationRepository: ReservationRepository,
  val workSlotRepository: WorkSlotRepository
) : ReservationPersistenceInterface {
  override fun saveAndValidate(
    reservation: Reservation,
    calendar: ReservationsCalendar
  ): Reservation {
    val jsonSchema = JSONObject(
      calendar.personalInformationSchema
    )
    val jsonSubject = JSONObject(
      reservation.personalInformation
    )
    val schema = SchemaLoader.load(jsonSchema)
    try {
      schema.validate(jsonSubject)
    } catch (e: Exception) {
      throw DataIntegrityViolationException(e.message ?: "invalid json")
    }
    if (reservation.services!!.isEmpty()) {
      throw DataIntegrityViolationException("services cannot be empty")
    }
    val duration = reservation.services!!.fold(0)
    { acc, service -> acc + service.duration!! }
    reservation.timeRange!!.endTime =
      reservation.timeRange!!.startTime!!.plusMinutes(duration.toLong())

    val workSlots =
      workSlotRepository.findByCalendarAndDay(calendar, reservation.day!!)
    val validWorkSlots =
      workSlots.filter { workSlot ->
        checkIfTimeRangeInWorkSlot(
          workSlot,
          reservation.timeRange!!
        )
      }
    if (validWorkSlots.isEmpty())
      throw DataIntegrityViolationException("no work slot for reservation")
    if (validWorkSlots.size > 1)
      throw DataIntegrityViolationException("multiple work slots for reservation")
    val savedReservation = reservationRepository.save(reservation) ?:
        throw DataIntegrityViolationException("could not save reservation")
    validWorkSlots[0].reservations.add(savedReservation)
    validWorkSlots[0].largestGap = calcLargestGap(validWorkSlots[0])
    workSlotRepository.save(validWorkSlots[0])
    return savedReservation
  }
}
