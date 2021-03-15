import { ConfigWorkSlot } from '@/models/WorkSlotModel';
import { Service } from '@/models/Service';

export default class CalendarModel {
  id: string;
  calendarName: string;
  route: string;
  createdAt: string;
  numberOfEntries: number;
  published: boolean;
  workSlotConfiguration: ConfigWorkSlot[] = [];
  services: Service[] = [];

  constructor(obj) {
    obj && Object.assign(this, obj);
  }
}