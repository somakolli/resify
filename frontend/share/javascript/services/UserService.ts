import {HttpHelper} from "../helpers/HttpHelper";
import {AuthProvider} from "../helpers/auth/AuthProvider";
import {User} from "../models/User";

export class UserService {
  url: string;
  httpHelper: HttpHelper;

  constructor(baseUrl: string, authProvider: AuthProvider) {
    this.url = baseUrl;
    this.httpHelper = new HttpHelper(authProvider)
  }

  async getMe(): Promise<User> {
    return await this.httpHelper.getDataAuthenticated<User>(
      this.url + "/api/users/me"
    );
  }
}
