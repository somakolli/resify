class IconRepository {
  public icons: Map<string, string> = new Map([
    [
      'calendar',
      `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path></svg>`,
    ],
    [
      'stats',
      `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 3.055A9.001 9.001 0 1020.945 13H11V3.055z"></path><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20.488 9H15V3.512A9.025 9.025 0 0120.488 9z"></path></svg>`,
    ],
    [
      'addCalendar',
      `<svg viewBox="0 0 20 20" fill="none" stroke="currentColor" xmlns="http://www.w3.org/2000/svg">
          <path d="M6 5V3M6 3V1M6 3H14M6 3H3C2.46957 3 1.96086 3.21071 1.58579 3.58578C1.21071 3.96086 1 4.46957 1 5V17C1 17.5304 1.21071 18.0391 1.58579 18.4142C1.96086 18.7893 2.46957 19 3 19H17C17.5304 19 18.0391 18.7893 18.4142 18.4142C18.7893 18.0391 19 17.5304 19 17V5C19 4.46957 18.7893 3.96086 18.4142 3.58578C18.0391 3.21071 17.5304 3 17 3H14M14 5V3M14 3V1M10 8V11M10 11V14M10 11H13M10 11H7" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
       </svg>`,
    ],
    [
      'eye',
      `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"></path></svg>`,
    ],
    [
      'eye-off',
      `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.88 9.88l-3.29-3.29m7.532 7.532l3.29 3.29M3 3l3.59 3.59m0 0A9.953 9.953 0 0112 5c4.478 0 8.268 2.943 9.543 7a10.025 10.025 0 01-4.132 5.411m0 0L21 21"></path></svg>`,
    ],
    [
      'arrow-left',
      `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"></path></svg>`,
    ],
    [
      'adjustments',
      `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6V4m0 2a2 2 0 100 4m0-4a2 2 0 110 4m-6 8a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4m6 6v10m6-2a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4"></path></svg>`,
    ],
    [
      'down',
      `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"></path></svg>`,
    ],
    [
      'arrow-right',
      `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path></svg>`,
    ],
    [
      'pencil',
      `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z"></path></svg>`,
    ],
    [
      'plus',
      `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6"></path></svg>`,
    ],
    [
      'x-circle',
      `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>`,
    ],
    [
      'trash',
      `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path></svg>`,
    ],
    [
      'information-circle',
      `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>`,
    ],
    [
      'clipboard',
      `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"></path></svg>`,
    ],
    [
      'logout',
      `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"></path></svg>`,
    ],
    [
      'logo',
      `<svg viewBox="0 0 70 70" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path fill-rule="evenodd" clip-rule="evenodd" d="M15 0C6.71573 0 0 6.71573 0 15V55C0 63.2843 6.71573 70 15 70H55C63.2843 70 70 63.2843 70 55V15C70 6.71573 63.2843 0 55 0H15ZM15 12.5C13.6193 12.5 12.5 13.6193 12.5 15V22.936C12.5 24.3167 13.6193 25.436 15 25.436C16.3807 25.436 17.5 24.3167 17.5 22.936V17.5H31.0362C32.4169 17.5 33.5362 16.3807 33.5362 15C33.5362 13.6193 32.4169 12.5 31.0362 12.5H15ZM24.5888 26.9C23.208 26.9 22.0888 28.0193 22.0888 29.4C22.0888 30.7807 23.208 31.9 24.5888 31.9H46.1841C47.5648 31.9 48.6841 30.7807 48.6841 29.4C48.6841 28.0193 47.5648 26.9 46.1841 26.9H24.5888ZM57.5 31.48C57.5 30.0993 56.3807 28.98 55 28.98C53.6193 28.98 52.5 30.0993 52.5 31.48V52.5H22.4671C21.0864 52.5 19.9671 53.6193 19.9671 55C19.9671 56.3807 21.0864 57.5 22.4671 57.5H55C56.3807 57.5 57.5 56.3807 57.5 55V31.48Z" fill="#047857"/>
       </svg>`,
    ],
    [
      'name',
      `<svg width="121" height="50" viewBox="0 0 95 39" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M2.76001 27.6C2.44001 27.6 2.10668 27.5467 1.76001 27.44C1.44001 27.3333 1.17334 27.08 0.960007 26.68C0.746673 26.28 0.639999 25.6667 0.639999 24.84C0.639999 24.3067 0.68001 23.6267 0.76001 22.8C0.866676 21.9467 0.973335 21.12 1.08 20.32C1.29334 18.9866 1.48 17.52 1.64 15.92C1.82667 14.32 1.92 12.7333 1.92 11.16C1.92 10.6 1.90667 10.0133 1.88 9.39999C1.85334 8.78666 1.8 8.19998 1.72 7.63998C1.56 7.71998 1.37334 7.79997 1.16 7.87997C0.973337 7.93331 0.813341 7.95999 0.680008 7.95999C0.413341 7.95999 0.226677 7.82666 0.12001 7.56C0.0400104 7.26666 0 6.93333 0 6.56C0 5.70666 0.200006 5.02666 0.600006 4.51999C1.02667 3.98666 1.65333 3.50665 2.48 3.07999C3.57333 2.46665 4.73334 2.02665 5.96001 1.75998C7.21334 1.46665 8.4 1.31998 9.52 1.31998C11.1733 1.31998 12.64 1.61332 13.92 2.19998C15.2 2.78665 16.2 3.61333 16.92 4.67999C17.64 5.74666 18 7.02666 18 8.51999C18 10.0667 17.6267 11.44 16.88 12.64C16.16 13.84 15.2267 14.8266 14.08 15.6C12.96 16.3466 11.8133 16.84 10.64 17.08C11.0667 17.7467 11.52 18.4133 12 19.08C12.5067 19.7467 13.0133 20.3466 13.52 20.88C15.04 22.4533 16.5867 23.24 18.16 23.24C18.5867 23.24 18.96 23.2133 19.28 23.16C19.44 23.1333 19.5733 23.2 19.68 23.36C19.8133 23.4933 19.88 23.7733 19.88 24.2C19.88 24.7066 19.7733 25.2266 19.56 25.76C19.3467 26.2666 18.9867 26.6933 18.48 27.04C17.9733 27.3866 17.28 27.56 16.4 27.56C15.36 27.56 14.36 27.2133 13.4 26.52C12.4667 25.8267 11.6 24.9466 10.8 23.88C10 22.7866 9.28 21.64 8.64 20.44C8.02667 19.24 7.50667 18.1333 7.08 17.12C6.97334 16.8267 6.88 16.5467 6.8 16.28C6.72 15.9867 6.68001 15.7067 6.68001 15.44C6.68001 14.9867 6.82668 14.6133 7.12001 14.32C7.33334 14.1066 7.62667 13.96 8 13.88C8.4 13.8 8.97333 13.76 9.72 13.76C11.1333 13.76 12.28 13.3733 13.16 12.6C14.04 11.8266 14.48 10.8266 14.48 9.59998C14.48 8.26664 13.9733 7.29333 12.96 6.67999C11.9733 6.06666 10.76 5.75998 9.32001 5.75998C8.14667 5.75998 6.92 5.94664 5.64 6.31998C5.56 8.05331 5.37334 10.04 5.08 12.28C4.92 13.6133 4.77333 14.9333 4.64 16.24C4.53333 17.5467 4.48 18.7333 4.48 19.8C4.48 20.7333 4.56 21.56 4.72 22.28C4.90667 22.9733 5.26667 23.5733 5.8 24.08C6.17334 24.4533 6.36 24.8533 6.36 25.28C6.36 25.8667 6.02667 26.4 5.36 26.88C4.69333 27.36 3.82668 27.6 2.76001 27.6Z" fill="#047857"/>
        <path d="M27.9999 27.72C25.9733 27.72 24.3866 27.1467 23.2399 26C22.1199 24.8267 21.5599 23.2 21.5599 21.12C21.5599 19.2267 21.9733 17.5067 22.7999 15.96C23.6266 14.4133 24.7333 13.1867 26.1199 12.28C27.5333 11.3467 29.0799 10.88 30.7599 10.88C32.3599 10.88 33.6399 11.28 34.5999 12.08C35.5599 12.8533 36.0399 13.8933 36.0399 15.2C36.0399 16.24 35.7199 17.2 35.0799 18.08C34.4399 18.96 33.5866 19.6666 32.5199 20.2C31.4533 20.7066 30.2666 20.96 28.9599 20.96C27.3599 20.96 26.0533 20.6 25.0399 19.88C25.0666 21.1066 25.4799 22.0933 26.2799 22.84C27.1066 23.5867 28.1599 23.96 29.4399 23.96C30.2133 23.96 30.9999 23.8267 31.7999 23.56C32.6266 23.2667 33.3333 22.88 33.9199 22.4C34.2933 22.08 34.6399 21.92 34.9599 21.92C35.2266 21.92 35.4399 22.0133 35.5999 22.2C35.7866 22.36 35.8799 22.5733 35.8799 22.84C35.8799 23.4267 35.3733 24.2 34.3599 25.16C32.5733 26.8667 30.4533 27.72 27.9999 27.72ZM28.5999 18.32C29.5599 18.32 30.3599 18.12 30.9999 17.72C31.6399 17.32 31.9599 16.8133 31.9599 16.2C31.9599 15.6933 31.7466 15.3066 31.3199 15.04C30.8933 14.7466 30.3066 14.6 29.5599 14.6C28.5733 14.6 27.7066 14.8667 26.9599 15.4C26.2399 15.9067 25.7199 16.6267 25.3999 17.56C25.7733 17.8 26.2533 17.9867 26.8399 18.12C27.4266 18.2533 28.0133 18.32 28.5999 18.32Z" fill="#047857"/>
        <path d="M44.3999 27.72C43.4132 27.72 42.4933 27.5733 41.6399 27.28C40.7866 26.9867 40.0932 26.5733 39.5599 26.04C39.0266 25.5066 38.7599 24.8667 38.7599 24.12C38.7599 23.72 38.8532 23.3467 39.0399 23C39.2533 22.6267 39.5866 22.44 40.0399 22.44C40.2799 22.44 40.5333 22.5067 40.7999 22.64C41.2799 22.9067 41.8933 23.16 42.6399 23.4C43.3866 23.6133 44.1732 23.72 44.9999 23.72C45.9332 23.72 46.7599 23.5866 47.4799 23.32C48.1999 23.0533 48.5599 22.68 48.5599 22.2C48.5599 21.8266 48.2799 21.56 47.7199 21.4C47.1866 21.24 46.2799 21.0666 44.9999 20.88C44.1199 20.72 43.2666 20.5333 42.4399 20.32C41.6132 20.1066 40.9332 19.7467 40.3999 19.24C39.8666 18.7067 39.5999 17.9333 39.5999 16.92C39.5999 15.9333 39.8666 14.9867 40.3999 14.08C40.9599 13.1467 41.7866 12.3733 42.8799 11.76C43.9732 11.1466 45.3066 10.84 46.8799 10.84C47.7066 10.84 48.5066 10.9333 49.2799 11.12C50.0532 11.3067 50.6933 11.5867 51.1999 11.96C51.7066 12.3333 51.9599 12.8 51.9599 13.36C51.9599 13.84 51.7866 14.28 51.4399 14.68C51.1199 15.08 50.7332 15.4133 50.2799 15.68C49.8532 15.92 49.4933 16.04 49.1999 16.04C48.9866 16.04 48.7332 15.8933 48.4399 15.6C47.9866 15.2266 47.4799 14.9733 46.9199 14.84C46.3866 14.68 45.8532 14.6 45.3199 14.6C44.5199 14.6 43.8666 14.72 43.3599 14.96C42.8799 15.2 42.6399 15.5066 42.6399 15.88C42.6399 16.2266 42.8399 16.5067 43.2399 16.72C43.6666 16.9333 44.1866 17.0933 44.7999 17.2C45.4133 17.28 45.9999 17.36 46.5599 17.44C48.1066 17.6533 49.3332 18.0267 50.2399 18.56C51.1466 19.0667 51.5999 19.9733 51.5999 21.28C51.5999 22.2933 51.3066 23.3066 50.7199 24.32C50.1599 25.3066 49.3332 26.12 48.2399 26.76C47.1732 27.4 45.8932 27.72 44.3999 27.72Z" fill="#047857"/>
        <path d="M58.3999 7.31999C57.6532 7.29332 57.0532 7.06666 56.5999 6.64C56.1465 6.21333 55.9199 5.61334 55.9199 4.84001C55.9465 4.01334 56.2265 3.34668 56.7599 2.84001C57.2932 2.30668 57.9732 2.05333 58.7999 2.08C59.5732 2.10667 60.1732 2.33333 60.5999 2.75999C61.0532 3.18666 61.2665 3.78668 61.2399 4.56001C61.2132 5.36001 60.9465 6.02668 60.4398 6.56001C59.9332 7.06668 59.2532 7.31999 58.3999 7.31999ZM58.4398 27.6C57.6399 27.6 57.0132 27.3867 56.5599 26.96C56.1065 26.5067 55.8799 25.7467 55.8799 24.68C55.8799 24.0667 55.9332 23.4667 56.0399 22.88C56.1732 22.2933 56.3199 21.72 56.4799 21.16C56.6665 20.5734 56.8132 19.9733 56.9199 19.36C57.0532 18.7467 57.1199 18.0933 57.1199 17.4C57.1199 16.92 57.0532 16.4667 56.9199 16.04C56.7865 15.5867 56.4932 15.2533 56.0399 15.04C55.6932 14.8533 55.3865 14.6667 55.1199 14.48C54.8799 14.2667 54.7599 14 54.7599 13.68C54.7599 13.3067 54.9332 12.9067 55.2799 12.48C55.6265 12.0533 56.0799 11.6933 56.6399 11.4C57.2265 11.08 57.8265 10.92 58.4398 10.92C59.1065 10.92 59.5865 11.0933 59.8799 11.44C60.1999 11.7867 60.4132 12.2267 60.5199 12.76C60.6265 13.2933 60.6799 13.84 60.6799 14.4C60.6799 15.2533 60.6132 16.0933 60.4799 16.92C60.3465 17.72 60.2132 18.4667 60.0799 19.16C59.9732 19.6667 59.8932 20.1333 59.8399 20.56C59.7865 20.9867 59.7599 21.3867 59.7599 21.76C59.7599 22.4267 59.9199 22.9333 60.2399 23.28C60.5599 23.6 61.0132 23.9067 61.5999 24.2C61.8399 24.3333 61.9599 24.5333 61.9599 24.8C61.9599 25.0133 61.8532 25.3467 61.6399 25.8C61.4265 26.2533 61.0532 26.6667 60.5199 27.04C60.0132 27.4133 59.3199 27.6 58.4398 27.6Z" fill="#047857"/>
        <path d="M67.3198 34.88C66.8132 34.88 66.4265 34.7466 66.1598 34.48C65.8932 34.24 65.7732 33.7867 65.7998 33.12C65.7998 32.72 65.8265 32 65.8798 30.96C65.9598 29.9467 66.0532 28.7333 66.1598 27.32C66.2932 25.9333 66.4265 24.48 66.5598 22.96C66.7198 21.44 66.8665 19.9733 66.9998 18.56C67.1332 17.1467 67.2532 15.9333 67.3598 14.92C67.0932 14.9466 66.8398 14.9733 66.5998 15C66.3598 15 66.1198 15.0267 65.8798 15.08C65.6132 15.1333 65.4132 15.16 65.2798 15.16C64.9598 15.16 64.7465 14.9733 64.6398 14.6C64.5332 14.2 64.4798 13.8266 64.4798 13.48C64.4798 13.08 64.5332 12.7466 64.6398 12.48C64.7732 12.2133 65.0398 12 65.4398 11.84C65.6798 11.7333 65.9865 11.64 66.3598 11.56C66.7598 11.48 67.1732 11.4133 67.5998 11.36C67.7332 9.78665 67.9332 8.29331 68.1998 6.87997C68.4932 5.46664 68.9332 4.26667 69.5198 3.28C70.1332 2.26667 70.9598 1.46664 71.9998 0.879974C73.0665 0.293308 74.1465 0 75.2398 0C76.0398 0 76.7865 0.15998 77.4798 0.47998C78.1998 0.773314 78.7732 1.19998 79.1998 1.75998C79.6532 2.29331 79.8798 2.90664 79.8798 3.59998C79.8798 4.10664 79.7465 4.52 79.4798 4.84C79.2398 5.16 78.9598 5.31998 78.6398 5.31998C78.5065 5.31998 78.3598 5.29332 78.1998 5.23999C78.0398 5.18666 77.8798 5.13332 77.7198 5.07999C77.4265 4.94665 77.0532 4.83998 76.5998 4.75998C76.1732 4.65331 75.7065 4.59998 75.1998 4.59998C74.1598 4.59998 73.3332 4.88 72.7198 5.44C72.1331 6 71.6798 6.77331 71.3598 7.75998C71.0665 8.71998 70.8265 9.84 70.6398 11.12C71.1998 11.0667 71.7998 11.0133 72.4398 10.96C73.0798 10.9067 73.5998 10.88 73.9998 10.88C74.6398 10.88 75.1332 10.9866 75.4798 11.2C75.8532 11.3866 76.0398 11.76 76.0398 12.32C76.0398 12.6666 75.9732 13.08 75.8398 13.56C75.7332 14.0133 75.5598 14.4267 75.3198 14.8C75.0798 15.1467 74.7998 15.32 74.4798 15.32C74.3465 15.32 74.1198 15.28 73.7998 15.2C73.4532 15.1466 72.9998 15.08 72.4398 15C71.8798 14.92 71.1732 14.8667 70.3198 14.84C70.2132 15.9867 70.1198 17.2667 70.0398 18.68C69.9865 20.0667 69.9598 21.5333 69.9598 23.08C69.9598 24.76 70.0132 26.2133 70.1198 27.44C70.2265 28.6667 70.3332 29.6667 70.4398 30.44C70.4931 30.7867 70.5332 31.0933 70.5598 31.36C70.5865 31.6267 70.5998 31.84 70.5998 32C70.5998 32.7733 70.4132 33.36 70.0398 33.76C69.6665 34.1866 69.2265 34.48 68.7198 34.64C68.2132 34.8 67.7465 34.88 67.3198 34.88Z" fill="#047857"/>
        <path d="M79.0798 38.24C77.9865 38.24 77.0531 37.9467 76.2798 37.36C75.4798 36.7733 75.0798 35.9867 75.0798 35C75.0798 34.2267 75.3198 33.56 75.7998 33C76.2531 32.4667 76.8265 32.2 77.5198 32.2C77.8398 32.2 78.0665 32.2533 78.1998 32.36C78.3331 32.4667 78.4398 32.5867 78.5198 32.72C78.7331 33.0667 78.9731 33.36 79.2398 33.6C79.5331 33.8666 79.9198 34 80.3998 34C81.0398 34 81.6531 33.7733 82.2398 33.32C82.8531 32.8666 83.4131 32.3466 83.9198 31.76C84.3731 31.2533 84.9198 30.52 85.5598 29.56C86.1998 28.6267 86.8398 27.56 87.4798 26.36C86.8931 26.8667 86.3465 27.2267 85.8398 27.44C85.3331 27.68 84.8531 27.8 84.3998 27.8C83.8398 27.8 83.2931 27.6 82.7598 27.2C82.2265 26.7733 81.8931 25.9867 81.7598 24.84C81.6798 23.96 81.5198 22.9866 81.2798 21.92C81.0665 20.8533 80.7865 19.8267 80.4398 18.84C80.1198 17.8267 79.7198 16.9733 79.2398 16.28C79.0531 15.96 78.8398 15.72 78.5998 15.56C78.3598 15.4 78.0131 15.2933 77.5598 15.24C77.2398 15.2133 77.0265 15.12 76.9198 14.96C76.8398 14.8 76.7998 14.5466 76.7998 14.2C76.7998 13.24 77.0665 12.4666 77.5998 11.88C78.1331 11.2933 78.8531 11 79.7598 11C80.8531 11 81.6398 11.4267 82.1198 12.28C82.5998 13.1067 83.0131 14.12 83.3598 15.32C83.4931 15.8 83.6265 16.44 83.7598 17.24C83.9198 18.0133 84.0531 18.8533 84.1598 19.76C84.2931 20.6666 84.3865 21.5333 84.4398 22.36C84.4665 22.9467 84.7998 23.24 85.4398 23.24C86.2931 23.24 87.1065 22.9733 87.8798 22.44C88.6531 21.9067 89.2798 21.2133 89.7598 20.36C90.2665 19.5067 90.5198 18.6133 90.5198 17.68C90.5198 17.1467 90.4531 16.6667 90.3198 16.24C90.1865 15.8133 89.9598 15.44 89.6398 15.12C89.3731 14.8 89.1598 14.5467 88.9998 14.36C88.8398 14.1467 88.7598 13.9067 88.7598 13.64C88.7598 13.1867 88.9731 12.76 89.3998 12.36C89.8264 11.96 90.3331 11.6267 90.9198 11.36C91.5331 11.0933 92.0665 10.96 92.5198 10.96C93.3198 10.96 93.8531 11.3467 94.1198 12.12C94.3864 12.8667 94.4531 13.6933 94.3198 14.6C94.1598 15.8533 93.8398 17.3333 93.3598 19.04C92.8798 20.7466 92.2531 22.5333 91.4798 24.4C90.7331 26.2667 89.8665 28.0667 88.8798 29.8C87.9198 31.56 86.8931 33.0933 85.7998 34.4C84.8665 35.5467 83.8264 36.4667 82.6798 37.16C81.5331 37.88 80.3331 38.24 79.0798 38.24Z" fill="#047857"/>
      </svg>`,
    ],
    [
      'exclamation-circle',
      `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>`,
    ],
  ]);
}

export const iconRepository: IconRepository = new IconRepository();
