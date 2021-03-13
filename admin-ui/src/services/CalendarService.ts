import { url as baseUrl } from '@/config/url-config';
import {
  getDataAuthenticated,
  postDataAuthenticated
} from '@/helpers/HttpHelper';
import CalendarModel from '@/models/CalendarModel';
export class CalendarService {
  url = baseUrl + '/api/calendars/';

  onlyLettersAndDigits(str: string) {
    return str.match('^[a-zA-Z0-9 ]+$');
  }

  async createCalendar(calendarName: string) {
    if (!this.onlyLettersAndDigits(calendarName))
      throw new Error('Not only letters and digits.');
    const route = calendarName.replace(' ', '-');
    return await postDataAuthenticated<
      CalendarModel,
      { calendarName: string; route: string }
    >(this.url, {
      calendarName: calendarName,
      route: route
    });
  }
  async getCalendar(route: string): Promise<CalendarModel> {
    return await getDataAuthenticated<CalendarModel>(this.url + route);
  }
  async changeName(calendarName: string) {
    throw new Error('not implemented!');
  }
  async deleteCalendar(route: string) {
    throw new Error('not implemented');
  }
}
