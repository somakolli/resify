import { Time } from '@/models/Time';

export class TimeRange {
  startTime: Time;
  endTime: Time;
  static fromServerResponse(serverResponse) {
    const timeRange = new TimeRange();
    timeRange.startTime = Time.fromServerResponse(serverResponse.startTime);
    timeRange.endTime = Time.fromServerResponse(serverResponse.endTime);
    return timeRange;
  }
  get duration(): number {
    return this.endTime.getTotalMinutes() - this.startTime.getTotalMinutes();
  }
}
