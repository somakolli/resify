package de.freshspark.resify.repositories

import com.reservationappservice.Models.*
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.*

interface UserRepository : JpaRepository<ResifyUser, UUID> {
    fun findByEmail(email: String): ResifyUser?
}

interface CalendarRepository : JpaRepository<ReservationsCalendar, UUID> {
    fun findAllByResifyUser(resifyUser: ResifyUser): List<ReservationsCalendar>
    fun findByRoute(route: String): ReservationsCalendar?
    fun findAllByResifyUserEmail(email: String): List<ReservationsCalendar>?
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
