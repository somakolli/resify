export class Messages {
  badEmailFormat: string;
  emailAlreadyInUse: string;
  enterEmail: string;
  enterPassword: string;
  emailDoesNotExist: string;
  badPassword: string;
  weakPasswordShort: string;
  userNotConfirmed: string;
  unknownError: string;
}

export enum Locale {
  de = 'de',
  en = 'en'
}

export const messages: Map<Locale, Messages> = new Map([
  [
    Locale.en,
    {
      badEmailFormat: 'Bad Email format!',
      emailAlreadyInUse: 'Email Already in use!',
      enterEmail: 'Please enter an Email!',
      enterPassword: 'Please enter a Password!',
      emailDoesNotExist: 'Email does not Exist!',
      badPassword: 'Password is incorrect!',
      weakPasswordShort: 'The password must to weak!',
      userNotConfirmed:
        'Contact an administrator to confirm your Registration.',
      unknownError: 'An unknown error occurred.'
    }
  ],
  [
    Locale.de,
    {
      badEmailFormat: 'Ungültiges Email format.',
      emailAlreadyInUse: 'Email Addresse ist bereits registriert.',
      enterEmail: 'Geben Sie bitte eine Email-Addressen ein.',
      enterPassword: 'Geben Sie bitten ein Passwort ein.',
      emailDoesNotExist: 'Diese Email-Adresse ist nicht vergeben',
      badPassword: 'Falsches Password!',
      weakPasswordShort: 'Passwort zu schwach',
      userNotConfirmed:
        'Kontaktieren sie einen Administrator, der ihre Registrierung bestätigt.',
      unknownError: 'Ein unbekannter Fehler is aufgetreten.'
    }
  ]
]);
