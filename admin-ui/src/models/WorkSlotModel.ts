import { MyDate } from '@/models/MyDate';
import { TimeRange } from '@/models/TimeRange';

export class WorkSlotModel {
  date: MyDate;
  timeRange: TimeRange;
  constructor(date: MyDate, timeRange: TimeRange) {
    this.date = date;
    this.timeRange = timeRange;
  }
}

export class ConfigWorkSlot {
  day: number;
  timeRange: TimeRange;
  static fromServerResponse(serverResponse) {
    const configWorkSlot = new ConfigWorkSlot();
    configWorkSlot.day = serverResponse.weekDay;
    configWorkSlot.timeRange = TimeRange.fromServerResponse(
      serverResponse.timeRange
    );
    return configWorkSlot;
  }
}
