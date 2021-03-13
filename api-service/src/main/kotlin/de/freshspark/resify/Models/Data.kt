package com.reservationappservice.Models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.sun.istack.NotNull
import io.quarkus.runtime.annotations.RegisterForReflection
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.LazyCollection
import java.time.*
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import javax.persistence.*

enum class Role {
    Admin, User
}

@Embeddable
class PersonalInformation(
    var name: String = "",
    var email: String = "",
    var phoneNumber: String = ""
)

@Embeddable
class TimeRange(
    var startTime: LocalTime? = null,
    var endTime: LocalTime? = null
) {

    fun inConflict(other: TimeRange): Boolean {
        return endTime!!.isAfter(other.startTime) &&
                startTime!!.isBefore(other.endTime)
    }
}

@Entity
data class Reservation
    (
    @Id @GeneratedValue
    var reservationId: UUID? = null,
    var day: LocalDate? = null,
    var timeRange: TimeRange? = null,
    var personalInformation: PersonalInformation? = null,
    @ManyToOne
    @JsonIgnore
    var workSlot: WorkSlot? = null,
    @ManyToOne
    var service: Service? = null
)

@Entity
data class Service(
    @Id @GeneratedValue
    var serviceId: UUID? = null,
    var name: String? = null,
    // length in minutes
    var duration: Int? = 0,
    @ManyToOne
    @JsonIgnore
    var calendar: ReservationsCalendar? = null
)

@Entity
data class WorkSlot
    (
    @Id @GeneratedValue
    var workSlotId: UUID? = null,
    var day: LocalDate? = null,
    var timeRange: TimeRange? = null,
    @ManyToOne
    @JsonIgnore
    var calendar: ReservationsCalendar? = null,
    @OneToMany
    var reservations: MutableCollection<Reservation> = mutableListOf()
)

@Entity
data class ConfigurationWorkSlot
    (
    @Id @GeneratedValue
    var workSlotId: UUID? = null,
    // Monday: 0, Tuesday: 1 ...
    var weekDay: Byte = 0,
    var timeRange: TimeRange? = null
    )

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(columnNames = ["resifyuser_userid", "route"])]
)
@RegisterForReflection
data class ReservationsCalendar
    (
    @Id @GeneratedValue
    var calendarId: UUID? = null,
    @ManyToOne
    @JsonIgnore
    var resifyUser: ResifyUser? = null,
    @NotNull
    @Column(unique = true)
    var route: String = "",
    @NotNull
    var calendarName: String = "",
    @OneToMany
    @Cascade(CascadeType.ALL)
    var services: MutableCollection<Service>? = mutableListOf(),
    @CreationTimestamp
    var createdAt: Date? = null,
    var published: Boolean? = false,
    var numberOfEntries: AtomicInteger? = AtomicInteger(0),
    @OneToMany
    @Cascade(CascadeType.ALL)
    var workSlotConfiguration: MutableCollection<ConfigurationWorkSlot>? =
        mutableListOf()
)

@Entity
data class ResifyUser
    (
    @Id @GeneratedValue
    var userId: UUID? = null,
    @Column(unique = true)
    var email: String = "",
    var role: Role = Role.User
)