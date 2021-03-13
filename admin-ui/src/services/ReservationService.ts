import { url as baseUrl } from '@/config/url-config';
import { PersonalInformation, Reservation } from '@/models/Reservation';
import {
  getDataAuthenticated,
  postDataAuthenticated
} from '@/helpers/HttpHelper';
import { MyDate } from '@/models/MyDate';
import { TimeRange } from '@/models/TimeRange';
export class ReservationService {
  url: string;
  constructor(calendarRoute: string) {
    this.url = `${baseUrl}/api/calendars/${calendarRoute}/reservations`;
  }
  async createReservation(reservation: Reservation) {
    return await postDataAuthenticated(this.url, reservation);
  }
  async retrieveReservations(date: MyDate): Promise<Reservation[]> {
    // in js january is 0 because it is a copy of java 1.0
    const newDate = new MyDate(date.year, date.month + 1, date.day);
    const reservations = await getDataAuthenticated<Array<any>>(
      this.url + '?day=' + newDate.toISOString()
    );
    console.log(reservations);
    return reservations.map(
      reservation =>
        new Reservation(
          reservation.reservationId,
          TimeRange.fromServerResponse(reservation.timeRange),
          new PersonalInformation('test')
        )
    );
  }
  async updateReservation(reservation: Reservation) {
    throw new Error('not implemented');
  }
  async deleteReservation(reservation: Reservation) {
    throw new Error('not implemented');
  }
}
