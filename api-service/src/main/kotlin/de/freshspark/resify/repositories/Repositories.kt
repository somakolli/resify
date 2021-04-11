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
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.ws.rs.ext.Provider

private interface UserJPARepository : JpaRepository<ResifyUser, UUID> {
  fun findByEmailAndCompany(email: String, company: Company): ResifyUser?
  fun findAllByCompany(company: Company): List<ResifyUser>?
  fun findByEmail(email: String): ResifyUser?
}


@Provider
class UserRepository {

  @Inject
  @field: Default
  private lateinit var userRepository: UserJPARepository

  fun findByEmailAndCompany(email: String, company: Company): ResifyUser? =
    userRepository.findByEmailAndCompany(email, company)
  fun findAllByCompany(company: Company): List<ResifyUser>? =
    userRepository.findAllByCompany(company)
  fun findByEmail(email: String): ResifyUser? =
    userRepository.findByEmail(email)
  fun save(user: ResifyUser): ResifyUser =
    userRepository.save(user)
  fun findAll(): MutableList<ResifyUser> =
    userRepository.findAll()
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


interface CompanyRepository : JpaRepository<Company, UUID> {
  fun findAllByNameContains(searchString: String): List<Company>?
}

private interface ReservationJPARepository : JpaRepository<Reservation, UUID> {
}

@Provider
class ReservationRepository(
) {

  @Inject
  @field: Default
  private lateinit var reservationRepository: ReservationJPARepository

  @Inject
  @field: Default
  lateinit var workSlotRepository: WorkSlotRepository

  fun saveAndValidate(
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
