export class Time {
  date: Date;

  constructor(hours: number, minutes: number) {
    this.date = new Date(0, 0, 0, hours, minutes, 0, 0);
  }
  public static fromDate(date: Date): Time {
    return new Time(date.getHours(), date.getMinutes());
  }
  public static fromServerResponse(serverReponse) {
    return new Time(serverReponse[0], serverReponse[1]);
  }
  public getLocaleString(locale: string): string {
    return this.date.toLocaleTimeString(locale, {
      hour: '2-digit',
      minute: '2-digit'
    });
  }
  public getTotalMinutes(): number {
    return this.hours * 60 + this.minutes;
  }
  public addMinutes(minutes: number): Time {
    return Time.fromDate(new Date(this.date.getTime() + minutes * 60_000));
  }
  get hours(): number {
    return this.date.getHours();
  }
  set hours(hours) {
    this.date = new Date(0, 0, 0, hours, this.minutes, 0, 0);
  }
  get minutes(): number {
    return this.date.getMinutes();
  }

  set minutes(minutes) {
    this.date = new Date(0, 0, 0, this.hours, minutes, 0, 0);
  }
}
