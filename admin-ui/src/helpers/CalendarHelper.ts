export default class CalendarHelper {
  public static getDayNames(locale: string, format: DateFormat): Array<string> {
    const baseDate = new Date(Date.UTC(2017, 0, 2)); // just a Monday
    const weekDays = [];
    for (let i = 0; i < 7; i++) {
      weekDays.push(baseDate.toLocaleDateString(locale, { weekday: format }));
      baseDate.setDate(baseDate.getDate() + 1);
    }
    return weekDays;
  }
  public static getDayName(date: Date, locale: string, format: DateFormat) {
    return date.toLocaleDateString(locale, { weekday: format });
  }
  public static getMonthName(
    date: Date,
    locale: string,
    format: DateFormat
  ): string {
    return date.toLocaleDateString(locale, { month: format });
  }
  public static getFirstDay(date: Date): number {
    // get day is 0 if its a sunday
    // to make it a monday subtract 1 and mod with 7
    // the % operator in javascript does not handle negative numbers correctly
    const day = new Date(date.getFullYear(), date.getMonth(), 1).getDay() - 1;
    return CalendarHelper.mod(day, 7);
  }
  public static addMonths(date: Date, monthsToAdd: number) {
    return new Date(date.getFullYear(), date.getMonth() + monthsToAdd, 1);
  }
  public static getNumberOfDays(date: Date): number {
    const m = date.getMonth() + 1;
    const y = date.getFullYear();
    return m === 2
      ? y & 3 || (!(y % 25) && y & 15)
        ? 28
        : 29
      : 30 + ((m + (m >> 3)) & 1);
  }
  public static getLastDay(date: Date): number {
    const day =
      new Date(
        date.getFullYear(),
        date.getMonth(),
        this.getNumberOfDays(date)
      ).getDay() - 1;
    return CalendarHelper.mod(day, 7);
  }
  private static mod(m: number, n: number) {
    return ((m % n) + n) % n;
  }
}
export type DateFormat = 'long' | 'short' | 'narrow' | undefined;
