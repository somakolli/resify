export class MyDate {
  year: number;
  month: number;
  day: number;

  constructor(year: number, month: number, day: number) {
    this.year = year;
    this.month = month;
    this.day = day;
  }
  private static pad(a: number, b: number) {
    return (1e15 + a + "").slice(-b);
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
  addDay(value: number) {
    const tempDate = this.toDate();
    tempDate.setDate(tempDate.getDate() + value);
    this.year = tempDate.getFullYear();
    this.month = tempDate.getMonth();
    this.day = tempDate.getDate();
    return this;
  }
  toISOString() {
    return [this.year, this.month, this.day]
      .map((value) => {
        return value < 1000 ? MyDate.pad(value, 2) : value;
      })
      .join("-");
  }
}
