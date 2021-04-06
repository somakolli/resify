import CalendarHelper from "../helpers/CalendarHelper";

export function addLeadingZero(a: number) {
  return (a < 10) ? '0' + a : a;
}
export class MyDate {
  year: number;
  month: number;
  day: number;

  constructor(year: number, month: number, day: number) {
    this.year = year;
    this.month = month;
    this.day = day;
  }
  toDate() {
    return new Date(this.year, this.month, this.day);
  }
  static fromDate(date: Date): MyDate {
    return new MyDate(date.getFullYear(), date.getMonth(), date.getDate());
  }
  static fromString(date: string): MyDate {
    const dateList = date.split("-").map((dateUnit) => parseInt(dateUnit));
    return this.fromArray(dateList);
  }
  static fromArray(date: Array<number>): MyDate {
    return new MyDate(date[0], date[1], date[2]);
  }
  static fromServerResponse(response: any) {
    return this.fromString(response);
  }
  static today() {
    return this.fromDate(new Date(Date.now()));
  }
  firstDateOfMonth() {
    return new MyDate(this.year, this.month, 1);
  }
  lastDateOfMonth() {
    return new MyDate(this.year, this.month, this.numberOfDays())
  }
  addDay(value: number) {
    const tempDate = this.toDate();
    tempDate.setDate(tempDate.getDate() + value);
    this.year = tempDate.getFullYear();
    this.month = tempDate.getMonth();
    this.day = tempDate.getDate();
    return this;
  }
  addMonth(value: number) {
    const tempDate = this.toDate();
    tempDate.setMonth(this.month + value)
    this.year = tempDate.getFullYear()
    this.month = tempDate.getMonth()
    this.day = tempDate.getDate()
    return this
  }
  toISOString() {
    return [this.year, this.month + 1, this.day]
      .map((value) => {
        return value < 1000 ? addLeadingZero(value) : value;
      })
      .join("-");
  }
  formatLong() {
    return this.day + ' ' + this.monthNameLong() + ' ' +  this.year;
  }
  dayNameShort(): string {
    return CalendarHelper.getDayName(this.toDate(), 'en', 'short')
  }
  dayNameLong(): string{
    return CalendarHelper.getDayName(this.toDate(), 'en', 'long')
  }
  monthNameLong() {
    return CalendarHelper.getMonthName(this.toDate(), 'en', 'long');
  }
  firstDay(): number {
    return CalendarHelper.getFirstDay(this.toDate())
  }
  lastDay(): number {
    return CalendarHelper.getLastDay(this.toDate())
  }
  numberOfDays(): number {
    return CalendarHelper.getNumberOfDays(this.toDate())
  }
  toJSON() {
    return this.toISOString()
  }
}
