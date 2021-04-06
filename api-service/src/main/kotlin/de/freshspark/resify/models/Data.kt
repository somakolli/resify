package de.freshspark.resify.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.sun.istack.NotNull
import io.quarkus.runtime.annotations.RegisterForReflection
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import java.time.*
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import javax.json.bind.annotation.JsonbTransient
import javax.persistence.*
import kotlin.collections.mutableListOf

enum class Role {
  Admin, User
}

enum class Visibility {
  Public, Private
}

enum class PermissionScope {
  All,
  Fields
}

enum class PermissionType(val binNum: Int) {
  Read(0b1),
  Write(0b10)
}

fun readPermission(resource: UUID?) = Permission(
  resource
)
fun writePermission(resource: UUID?) = Permission(
  resource, PermissionScope.All, PermissionType.Write
)


@Entity
open class Permission (
  open var resource: UUID? = null,
  open var scope: PermissionScope = PermissionScope.All,
  open var type: PermissionType = PermissionType.Read
    ) : ResifyObject() {
  override fun equals(other: Any?): Boolean =
    (other is Permission)
        && other.resource == resource
        && other.scope == scope
        && other.type == type

  override fun hashCode(): Int {
    var result = resource?.hashCode() ?: 0
    result = 31 * result + scope.hashCode()
    result = 31 * result + type.hashCode()
    return result
  }
}


@Embeddable
class TimeRange(
  var startTime: LocalTime? = null,
  var endTime: LocalTime? = null
) {

  fun inConflict(other: TimeRange): Boolean {
    return endTime!!.isAfter(other.startTime) &&
        startTime!!.isBefore(other.endTime)
  }
  fun duration(): Int = startTime!!.until(endTime, ChronoUnit.MINUTES).toInt()
}

@MappedSuperclass
open class ResifyObject (
  @Id @GeneratedValue
  open var id: UUID? = null
)

@Entity
open class Reservation
  (
  open var day: LocalDate? = null,
  open var timeRange: TimeRange? = null,
  open var personalInformation: String? = "",
  @ManyToOne
  @JsonbTransient
  open var workSlot: WorkSlot? = null,
  @ManyToMany
  open var services: MutableCollection<Service>? = mutableListOf()
): ResifyObject(), Comparable<Reservation> {
  override fun compareTo(other: Reservation): Int {
    return timeRange!!.startTime!!.compareTo(other.timeRange!!.startTime!!)
  }
}

@Entity
open class Company(
  open var name: String? = null,
  open var description: String? = null,
  @OneToOne
  @JsonbTransient
  @JsonIgnore
  open var owner: ResifyUser? = null,
  @OneToMany
  @JsonbTransient
  open var admins: MutableCollection<ResifyUser> = mutableListOf(),
  @OneToMany
  @JsonbTransient
  open var users: MutableCollection<ResifyUser> = mutableListOf(),
  open var visibility: Visibility = Visibility.Private
): ResifyObject()

@Entity
open class Service(
  open var name: String? = null,
  // length in minutes
  open var duration: Int? = 0,
  @ManyToOne
  @JsonbTransient
  open var calendar: ReservationsCalendar? = null
): ResifyObject()

@Entity
open class WorkSlot
  (
  open var day: LocalDate? = null,
  open var timeRange: TimeRange? = null,
  @ManyToOne
  @JsonbTransient
  open var calendar: ReservationsCalendar? = null,
  @OneToMany
  open var reservations: MutableCollection<Reservation> = mutableListOf(),
  open var largestGap: Int = Int.MAX_VALUE
): ResifyObject()

@Entity
open class ConfigurationWorkSlot
  (
  // Monday: 0, Tuesday: 1 ...
  open var weekDay: Byte = 0,
  open var timeRange: TimeRange? = null
): ResifyObject()

@Entity
@Table(
  uniqueConstraints = [UniqueConstraint(columnNames = ["company_id", "route"])]
)
@RegisterForReflection
open class ReservationsCalendar
  (
  @ManyToOne
  @JsonbTransient
  open var company: Company? = null,
  @OneToOne
  @JsonbTransient
  open var creator: ResifyUser? = null,
  @NotNull
  @Column(unique = true)
  open var route: String = "",
  @NotNull
  open var calendarName: String = "",
  @OneToMany
  @Cascade(CascadeType.ALL)
  open var services: MutableCollection<Service>? = mutableListOf(),
  @CreationTimestamp
  open var createdAt: Date? = null,
  open var published: Boolean? = false,
  open var numberOfEntries: AtomicInteger? = AtomicInteger(0),
  @OneToMany
  @Cascade(CascadeType.ALL)
  open var workSlotConfiguration: MutableCollection<ConfigurationWorkSlot>? =
    mutableListOf(),
  open var visibility: Visibility = Visibility.Private,
  @Column(columnDefinition = "text")
  open var personalInformationSchema: String = defaultPersonalInformation
): ResifyObject()

@Entity
open class ResifyUser
  (
  @Column(unique = true)
  open var email: String = "",
  open var role: Role = Role.User,
  @ManyToOne
  open var company: Company? = null,
  @OneToMany
  @JsonbTransient
  @JsonIgnore
  open var permissions: MutableCollection<Permission> = mutableListOf()
): ResifyObject()
