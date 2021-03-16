import { Auth } from '@aws-amplify/auth';
import { messages } from '@share/stores/Messages';
import { localeConfig } from '@/config/locale-config';
import { AuthProvider } from '@share/helpers/auth/AuthProvider';

export class AmazonAuthProvider implements AuthProvider {
  async getIdToken() {
    const currentSession = await Auth.currentSession();
    return currentSession.getIdToken().getJwtToken();
  }
  async isSignedIn() {
    try {
      await Auth.currentAuthenticatedUser();
      return true;
    } catch (e) {
      return false;
    }
  }
  errorHandler(error) {
    const myMessages = messages.get(localeConfig.locale);
    let emailError = '';
    let passwordError = '';
    let overallError = '';
    console.log(error.message);
    switch (error.message) {
      case 'Username should be an email.':
        emailError = myMessages.badEmailFormat;
        break;
      case 'User does not exist.':
        emailError = myMessages.emailDoesNotExist;
        break;
      case 'An account with the given email already exists.':
        emailError = myMessages.emailAlreadyInUse;
        break;
      case 'Incorrect username or password.':
        passwordError = myMessages.badPassword;
        break;
      case "1 validation error detected: Value at 'password' failed to satisfy constraint: Member must have length greater than or equal to 6":
        passwordError = myMessages.weakPasswordShort;
        break;
      case 'Password did not conform with policy: Password not long enough':
        passwordError = myMessages.weakPasswordShort;
        break;
      case 'Password did not conform with policy: Password must have uppercase characters':
        passwordError = myMessages.weakPasswordShort;
        break;
      case 'Password did not conform with policy: Password must have numeric characters':
        passwordError = myMessages.weakPasswordShort;
        break;
      case 'Password did not conform with policy: Password must have lowercase characters':
        passwordError = myMessages.weakPasswordShort;
        break;
      case 'User is not confirmed.':
        overallError = myMessages.userNotConfirmed;
        break;
      default:
        overallError = myMessages.unknownError;
    }
    return { emailError, passwordError, overallError };
  }
  async signIn(username: string, password: string) {
    await Auth.signIn(username, password).catch((error) => {
      throw this.errorHandler(error);
    });
  }
  async signUp(username: string, password: string) {
    try {
      await Auth.signUp({ username, password });
    } catch (error) {
      throw this.errorHandler(error);
    }
  }
  async signOut() {
    try {
      await Auth.signOut();
    } catch (error) {
      throw this.errorHandler(error);
    }
  }
}
