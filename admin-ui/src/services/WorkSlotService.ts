import { ConfigWorkSlot, WorkSlotModel } from '@/models/WorkSlotModel';
import {
  getDataAuthenticated,
  postDataAuthenticated
} from '@/helpers/HttpHelper';
import { url } from '@/config/url-config';
import { MyDate } from '@/models/MyDate';

export class WorkSlotService {
  workSlotUrl: string;
  configWorkSlotUrl: string;
  generateUrl: string;
  constructor(route: string) {
    const baseUrl = `${url}/api/calendars/${route}`;
    this.configWorkSlotUrl = `${baseUrl}/config-work-slots`;
    this.workSlotUrl = `${baseUrl}/workslots`;
    this.generateUrl = `${baseUrl}/work-slots/generate`;
  }
  async createWorkSlot(workSlot: WorkSlotModel) {
    return postDataAuthenticated<WorkSlotModel, WorkSlotModel>(
      this.configWorkSlotUrl,
      workSlot
    );
  }
  async createConfigWorkSlot(workSlot: ConfigWorkSlot) {
    const returnSlot = await postDataAuthenticated(this.configWorkSlotUrl, {
      weekDay: workSlot.day,
      timeRange: {
        startTime: workSlot.timeRange.startTime.getLocaleString('de'),
        endTime: workSlot.timeRange.endTime.getLocaleString('de')
      }
    });
    return ConfigWorkSlot.fromServerResponse(returnSlot);
  }
  async generateWorkSlots(length: number) {
    const startDate = MyDate.fromDate(new Date(Date.now()));
    await postDataAuthenticated(this.generateUrl, {
      startDate: startDate.toISOString(),
      endDate: startDate.addDay(length).toISOString()
    });
  }
}
