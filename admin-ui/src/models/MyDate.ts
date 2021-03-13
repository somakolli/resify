export class MyDate {
  year: number;
  month: number;
  day: number;

  constructor(year: number, month: number, day: number) {
    this.year = year;
    this.month = month;
    this.day = day;
  }
  private pad(a, b) {
    return (1e15 + a + '').slice(-b);
  }
  toDate() {
    return new Date(this.year, this.month, this.day);
  }
  static fromDate(date: Date): MyDate {
    return new MyDate(date.getFullYear(), date.getMonth(), date.getDate());
  }
  static fromServerResponse(response) {
    return new MyDate(response[0], response[1] - 1, response[2]);
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
      .map(value => {
        return value < 1000 ? this.pad(value, 2) : value;
      })
      .join('-');
  }
}
