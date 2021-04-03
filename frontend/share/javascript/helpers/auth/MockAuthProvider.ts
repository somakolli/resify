import { AuthProvider } from "./AuthProvider";

export class MockAuthProvider implements AuthProvider {
  mockAuthProviderUrl: string;
  constructor(url: string) {
    this.mockAuthProviderUrl = url;
  }
  async getIdToken(): Promise<string> {
    // eslint-disable-next-line @typescript-eslint/camelcase
    const urlParams = new URLSearchParams({ grant_type: "authorization_code" });
    const response = await fetch(this.mockAuthProviderUrl + "/token", {
      method: "POST",
      mode: "cors",
      cache: "no-cache",
      headers: {
        "content-type": "application/x-www-form-urlencoded",
      },
      redirect: "follow",
      referrerPolicy: "no-referrer",
      body: urlParams,
    });
    const tokenData = await response.json();
    return tokenData.id_token;
  }
  async isSignedIn(): Promise<boolean> {
    return true;
  }
  errorHandler(error: string): string {
    return "error";
  }

  async signIn(username: string, password: string): Promise<void> {
    return;
  }

  async signOut(): Promise<void> {
    return;
  }

  async signUp(username: string, password: string): Promise<void> {
    return;
  }
}
