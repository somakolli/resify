import { MyDate } from "../DateTime/MyDate";
import { AuthProvider } from "../helpers/auth/AuthProvider";
import { HttpHelper } from "../helpers/HttpHelper";
import { ConfigWorkSlot, WorkSlotModel } from "../models/WorkSlotModel";

export class WorkSlotService {
  workSlotUrl: string;
  configWorkSlotUrl: string;
  generateUrl: string;
  httpHelper: HttpHelper;
  constructor(route: string, url: string, authProvider: AuthProvider) {
    const baseUrl = `${url}/api/calendars/${route}`;
    this.configWorkSlotUrl = `${baseUrl}/config-work-slots`;
    this.workSlotUrl = `${baseUrl}/workslots`;
    this.generateUrl = `${baseUrl}/work-slots/generate`;
    this.httpHelper = new HttpHelper(authProvider);
  }
  async createWorkSlot(workSlot: WorkSlotModel) {
    return this.httpHelper.postDataAuthenticated<WorkSlotModel, WorkSlotModel>(
      this.configWorkSlotUrl,
      workSlot
    );
  }
  async createConfigWorkSlot(workSlot: ConfigWorkSlot) {
    const returnSlot = await this.httpHelper.postDataAuthenticated(
      this.configWorkSlotUrl,
      {
        weekDay: workSlot.day,
        timeRange: {
          startTime: workSlot.timeRange.startTime.getLocaleString("de"),
          endTime: workSlot.timeRange.endTime.getLocaleString("de"),
        },
      }
    );
    return ConfigWorkSlot.fromServerResponse(returnSlot);
  }
  async generateWorkSlots(length: number) {
    const startDate = MyDate.fromDate(new Date(Date.now()));
    await this.httpHelper.postDataAuthenticated(this.generateUrl, {
      startDate: startDate.toISOString(),
      endDate: startDate.addDay(length).toISOString(),
    });
  }
}
