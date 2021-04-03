import {HttpHelper} from "../helpers/HttpHelper";
import {AuthProvider} from "../helpers/auth/AuthProvider";
import {Company} from "../models/Company";

export class CompanyService {
  url: string;
  httpHelper: HttpHelper;

  constructor(baseUrl: string, authProvider: AuthProvider) {
    this.url = baseUrl + "/api/company/";
    this.httpHelper = new HttpHelper(authProvider);
  }

  async createCompany(name: string): Promise<Company> {
    if(name === '')
      throw {message: 'no-company-name'}
    return this.httpHelper.postDataAuthenticated<Company, {name: string}>(
      this.url,
      {
        name: name
      }
    );
  }
}
