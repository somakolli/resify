import { AmazonAuthProvider } from '@/AmazonAuthProvider';
import { MockAuthProvider } from '@share/helpers/auth/MockAuthProvider';
import { mockAuthProviderUrl } from './url-config';

export const authProvider = new MockAuthProvider(mockAuthProviderUrl);
