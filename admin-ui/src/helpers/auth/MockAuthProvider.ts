import { AuthProvider } from '@/helpers/auth/AuthProvider';
import { mockAuthProviderUrl } from '@/config/url-config';

export class MockAuthProvider implements AuthProvider {
  async getIdToken(): Promise<string> {
    // eslint-disable-next-line @typescript-eslint/camelcase
    const urlParams = new URLSearchParams({ grant_type: 'authorization_code' });
    const response = await fetch(mockAuthProviderUrl + '/token', {
      method: 'POST',
      mode: 'cors',
      cache: 'no-cache',
      headers: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      redirect: 'follow',
      referrerPolicy: 'no-referrer',
      body: urlParams
    });
    const tokenData = await response.json();
    return tokenData.id_token;
  }
  async isSignedIn() {
    return true;
  }
  errorHandler(error: string) {
    return 'error';
  }

  async signIn(username: string, password: string) {
    return;
  }

  async signOut() {
    return;
  }

  async signUp(username: string, password: string) {
    return;
  }
}
