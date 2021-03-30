import { TimeRange } from "../DateTime/TimeRange";
import {Service} from "./Service";
import {MyDate} from "../DateTime/MyDate";


export class Reservation {
  id: string;
  timeRange: TimeRange;
  personalInformation: any;
  day: MyDate;
  services: Service[] = new Array<Service>();
  constructor(
    id = '',
    timeRange: TimeRange,
    personalInformation: any
  ) {
    this.id = id;
    this.timeRange = timeRange;
    this.personalInformation = personalInformation;
  }

  toLocalString(locale: string): string {
    return (
      this.timeRange.startTime!.getLocaleString(locale) +
      " - " +
      this.timeRange.endTime!.getLocaleString(locale)
    );
  }
}
