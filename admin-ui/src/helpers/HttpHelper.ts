import { authProvider } from '@/config/auth-config';

export async function postData<ReturnType, DataType>(
  url: string,
  data: DataType,
  headers = {},
  contentType = 'application/json'
): Promise<ReturnType> {
  const response = await fetch(url, {
    method: 'POST',
    mode: 'cors',
    cache: 'no-cache',
    headers: {
      'content-type': contentType,
      ...headers
    },
    redirect: 'follow',
    referrerPolicy: 'no-referrer',
    body: JSON.stringify(data)
  });
  return response.json();
}

export async function postDataAuthenticated<ReturnType, DataType>(
  url: string,
  data: DataType
) {
  const idToken = await authProvider.getIdToken();
  return postData<ReturnType, DataType>(url, data, {
    Authorization: 'Bearer ' + idToken
  });
}

export async function getData<T>(url: string, headers = {}): Promise<T> {
  const response = await fetch(url, { headers: headers });
  return response.json();
}

export async function getDataAuthenticated<T>(url: string) {
  const idToken = await authProvider.getIdToken();
  return getData<T>(url, { Authorization: 'Bearer ' + idToken });
}
