package de.freshspark.resify.repositories

import de.freshspark.resify.models.*
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.*

interface UserRepository : JpaRepository<ResifyUser, UUID> {
    fun findTopByEmailContains(subString: String): List<ResifyUser>
    fun findByEmail(email: String): ResifyUser?
}

interface CalendarRepository : JpaRepository<ReservationsCalendar, UUID> {
    fun findByRouteAndCompany(route: String, company: Company): ReservationsCalendar?
    fun findByRouteAndCompanyName(route: String, companyName: String): ReservationsCalendar?
    fun findAllByCompany(company: Company): List<ReservationsCalendar>?
    fun findAllByCompanyName(companyName: String): List<ReservationsCalendar>?
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

interface ReservationRepository : JpaRepository<Reservation, UUID> {
}

interface CompanyRepository: JpaRepository<Company, UUID> {
}
