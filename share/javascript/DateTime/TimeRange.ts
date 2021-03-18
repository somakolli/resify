import { Time } from "./Time";

export class TimeRange {
  startTime?: Time;
  endTime?: Time;
  static fromServerResponse(serverResponse: any) {
    const timeRange = new TimeRange();
    timeRange.startTime = Time.fromServerResponse(serverResponse.startTime);
    timeRange.endTime = Time.fromServerResponse(serverResponse.endTime);
    return timeRange;
  }
  get duration(): number {
    if(!this.endTime && !this.startTime)
      return Number.NaN;
    return this.endTime!.getTotalMinutes() - this.startTime!.getTotalMinutes();
  }
}
