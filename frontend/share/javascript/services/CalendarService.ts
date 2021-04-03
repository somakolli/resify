import { AuthProvider } from "../helpers/auth/AuthProvider";
import { HttpHelper } from "../helpers/HttpHelper";
import CalendarModel from "../models/CalendarModel";
export class CalendarService {
  url: string;
  httpHelper: HttpHelper;

  constructor(baseUrl: string, authProvider: AuthProvider) {
    this.url = baseUrl + "/api/calendars/";
    this.httpHelper = new HttpHelper(authProvider);
  }

  onlyLettersAndDigits(str: string) {
    return str.match("^[a-zA-Z0-9 ]+$");
  }

  async getCalendars(): Promise<CalendarModel[]> {
    return await this.httpHelper.getDataAuthenticated<CalendarModel[]>(
      this.url
    );
  }

  async createCalendar(calendarName: string) {
    if (!this.onlyLettersAndDigits(calendarName))
      throw new Error("Not only letters and digits.");
    const route = calendarName.replace(" ", "-");
    return await this.httpHelper.postDataAuthenticated<
      CalendarModel,
      { calendarName: string; route: string }
    >(this.url, {
      calendarName: calendarName,
      route: route,
    });
  }
  async getCalendar(route: string): Promise<CalendarModel> {
    return await this.httpHelper.getDataAuthenticated<CalendarModel>(
      this.url + route
    );
  }
  async changeName(calendarName: string) {
    throw new Error("not implemented!");
  }
  async deleteCalendar(route: string) {
    throw new Error("not implemented");
  }
}
