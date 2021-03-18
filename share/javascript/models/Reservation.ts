import { TimeRange } from "../DateTime/TimeRange";

export class PersonalInformation {
  public _name: string;
  constructor(name: string) {
    this._name = name;
  }
  get name(): string {
    return this._name;
  }
}

export class Reservation {
  private id: string;
  private timeRange: TimeRange;
  private personalInformation: PersonalInformation;
  constructor(
    id: string,
    timeRange: TimeRange,
    personalInformation: PersonalInformation
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
