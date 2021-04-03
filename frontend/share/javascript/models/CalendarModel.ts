import { ConfigWorkSlot } from "./WorkSlotModel";
import { Service } from "./Service";

export default class CalendarModel {
  id?: string;
  calendarName?: string;
  route?: string;
  createdAt?: string;
  numberOfEntries?: number;
  published?: boolean;
  workSlotConfiguration?: ConfigWorkSlot[] = [];
  services?: Service[] = [];

  constructor(obj: any) {
    obj && Object.assign(this, obj);
  }
}
