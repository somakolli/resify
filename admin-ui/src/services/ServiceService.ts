// yeah it's a stupid class name but it is what it is -_('-')_-

import { url as baseUrl } from '@/config/url-config';
import { Service } from '@/models/Service';
import {
  getDataAuthenticated,
  postDataAuthenticated
} from '@/helpers/HttpHelper';
export class ServiceService {
  url: string;
  constructor(calendarRoute: string) {
    this.url = `${baseUrl}/api/calendars/${calendarRoute}/services/`;
  }
  async createService(service: Service): Promise<Service> {
    return postDataAuthenticated<Service, Service>(this.url, service);
  }
  async retrieveServices(): Promise<Service[]> {
    return getDataAuthenticated<Service[]>(this.url);
  }
  updateService(service: Service) {
    throw new Error('to be implemented');
  }
  deleteService(service: Service) {
    throw new Error('to be implemented');
  }
}
