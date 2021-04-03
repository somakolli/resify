export class Service {
  serviceId: string;
  name: string;
  duration: number;

  constructor(name: string, duration: number, id = "") {
    this.serviceId = id;
    this.name = name;
    this.duration = duration;
  }
}
