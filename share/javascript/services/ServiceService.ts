// yeah it's a stupid class name but it is what it is -_('-')_-

import { AuthProvider } from "../helpers/auth/AuthProvider";
import { HttpHelper } from "../helpers/HttpHelper";
import { Service } from "../models/Service";
export class ServiceService {
  url: string;
  httpHelper: HttpHelper;
  constructor(
    calendarRoute: string,
    baseUrl: string,
    authProvider: AuthProvider
  ) {
    this.url = `${baseUrl}/api/calendars/${calendarRoute}/services/`;
    this.httpHelper = new HttpHelper(authProvider);
  }
  async createService(service: Service): Promise<Service> {
    service.serviceId = "";
    return this.httpHelper.postDataAuthenticated<Service, Service>(
      this.url,
      service
    );
  }
  async retrieveServices(): Promise<Service[]> {
    return this.httpHelper.getDataAuthenticated<Service[]>(this.url);
  }
  updateService(service: Service) {
    throw new Error("to be implemented");
  }
  deleteService(service: Service) {
    throw new Error("to be implemented");
  }
}
