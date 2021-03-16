import { MyDate } from "../DateTime/MyDate";
import { TimeRange } from "../DateTime/TimeRange";
import { AuthProvider } from "../helpers/auth/AuthProvider";
import { HttpHelper } from "../helpers/HttpHelper";
import { PersonalInformation, Reservation } from "../models/Reservation";

export class ReservationService {
  url: string;
  httpHelper: HttpHelper;
  constructor(
    calendarRoute: string,
    baseUrl: string,
    authProvider: AuthProvider
  ) {
    this.url = `${baseUrl}/api/calendars/${calendarRoute}/reservations`;
    this.httpHelper = new HttpHelper(authProvider);
  }
  async createReservation(reservation: Reservation) {
    return await this.httpHelper.postDataAuthenticated(this.url, reservation);
  }
  async retrieveReservations(date: MyDate): Promise<Reservation[]> {
    // in js january is 0 because it is a copy of java 1.0
    const newDate = new MyDate(date.year, date.month + 1, date.day);
    const reservations = await this.httpHelper.getDataAuthenticated<Array<any>>(
      this.url + "?day=" + newDate.toISOString()
    );
    return reservations.map(
      (reservation) =>
        new Reservation(
          reservation.reservationId,
          TimeRange.fromServerResponse(reservation.timeRange),
          new PersonalInformation("test")
        )
    );
  }
  async updateReservation(reservation: Reservation) {
    throw new Error("not implemented");
  }
  async deleteReservation(reservation: Reservation) {
    throw new Error("not implemented");
  }
}
