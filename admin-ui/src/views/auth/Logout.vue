<template>
  <div>Loging out</div>
</template>
<script lang="ts">
import { useRouter } from 'vue-router';
import { authProvider } from '@/config/auth-config';

export default {
  setup() {
    const router = useRouter();
    authProvider.signOut().then(() => console.log('signOut'));
    router.push({ name: 'Login' });
  }
};
</script>
