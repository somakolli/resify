import { AuthProvider } from "./auth/AuthProvider";

export class HttpHelper {
  authProvider: AuthProvider | null;
  constructor(authProvider: AuthProvider | null) {
    this.authProvider = authProvider;
  }

  async postData<ReturnType, DataType>(
    url: string,
    data: DataType,
    headers = {},
    contentType = "application/json"
  ): Promise<ReturnType> {
    const response = await fetch(url, {
      method: "POST",
      mode: "cors",
      cache: "no-cache",
      headers: {
        "content-type": contentType,
        ...headers,
      },
      redirect: "follow",
      referrerPolicy: "no-referrer",
      body: JSON.stringify(data),
    });
    if(!response.ok) {
      throw await response.json();
    }
    return response.json();
  }

  async postDataAuthenticated<ReturnType, DataType>(
    url: string,
    data: DataType
  ): Promise<ReturnType> {
    if(!this.authProvider)
      throw "please provide auth provider"
    const idToken = await this.authProvider.getIdToken();
    return this.postData<ReturnType, DataType>(url, data, {
      Authorization: "Bearer " + idToken,
    });
  }

  async getData<T>(url: string, headers = {}): Promise<T> {
    const response = await fetch(url, { headers: headers });
    if(!response.ok)
      throw await response.json();
    return response.json();
  }

  async getDataAuthenticated<T>(url: string): Promise<T> {
    if(!this.authProvider)
      throw "please provide auth provider"
    const idToken = await this.authProvider.getIdToken();
    return this.getData<T>(url, { Authorization: "Bearer " + idToken });
  }
}
