import {HttpHelper} from "../helpers/HttpHelper";

export class PublicService {
  url: string;
  httpHelper = new HttpHelper(null);
  constructor(baseUrl: string) {
    this.url = baseUrl;
  }
}
