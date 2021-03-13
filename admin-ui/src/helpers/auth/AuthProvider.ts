export interface AuthProvider {
  errorHandler(error: string);
  signIn(username: string, password: string);
  signUp(username: string, password: string);
  signOut();
  isSignedIn(): Promise<boolean>;
  getIdToken(): Promise<string>;
}
