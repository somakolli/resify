export interface AuthProvider {
  errorHandler(error: string): void;
  signIn(username: string, password: string): void;
  signUp(username: string, password: string): void;
  signOut(): void;
  isSignedIn(): Promise<boolean>;
  getIdToken(): Promise<string>;
}
