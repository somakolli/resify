<template>
  <div class="w-full flex flex-col items-center">
    <div class="mt-7 flex flex-col items-center">
      <Icon class="w-16 h-16" icon-name="logo"></Icon>
      <Icon class="w-max h-max mt-5" icon-name="name"></Icon>
    </div>
    <div class="w-10/12 mt-10">
      <div class="flex flex-col">
        <LabelInput
          v-model="username"
          :error-text="emailError"
          placeholder="Enter your Email"
          class="w-full"
          type="text"
        >
          Email
        </LabelInput>
        <LabelInput
          v-model="password"
          :error-text="passwordError"
          class="mt-5"
          type="password"
          placeholder="Enter your Password"
          >Password
        </LabelInput>
      </div>
    </div>
    <div class="flex flex-col w-36 mt-10">
      <Button primary class="h-10" @click="login">Sign In</Button>
      <Button text class="h-10 mt-3" @click="register">Register</Button>
    </div>
    <div>
      <div class="w-full flex items-center mt-5">
        <div class="bg-gray-200 h-px mx-5 flex-grow"></div>
        <span>or</span>
        <div class="bg-gray-200 h-px mx-5 flex-grow"></div>
      </div>
    </div>
    <div></div>
    <div class="w-full"></div>
  </div>
  <PopUpModal v-if="notConfirmed" @close="notConfirmed = false">
    <div class="flex items-center text-center w-full h-full">
      {{ myMessages.userNotConfirmed }}
    </div>
  </PopUpModal>
</template>
<script lang="ts">
import { ref, watchEffect } from 'vue';
import { useRouter } from 'vue-router';

import LabelInput from '../../components/LabelInput.vue';
import PopUpModal from '../../components/PopUpModal.vue';
import { localeConfig } from '@/config/locale-config';
import { messages } from '@share/stores/Messages';
import { authProvider } from '@/config/auth-config';
import Button from '@/components/Button.vue';
import Icon from '@components/Icon.vue';

export default {
  components: { PopUpModal, Button, LabelInput, Icon },
  setup() {
    const router = useRouter();
    const username = ref('');
    const password = ref('');
    const emailError = ref('');
    const passwordError = ref('');
    const notConfirmed = ref(false);
    const myMessages = messages.get(localeConfig.locale);

    function checkInput(): boolean {
      emailError.value = username.value ? '' : myMessages.enterEmail;
      passwordError.value = password.value ? '' : myMessages.enterPassword;
      return !emailError.value && !passwordError.value;
    }

    function handleError(error) {
      emailError.value = error.emailError;
      passwordError.value = error.passwordError;
      if (error.overallError !== '') {
        notConfirmed.value = true;
      }
    }

    async function login() {
      if (!checkInput()) return;
      try {
        await authProvider.signIn(username.value, password.value);
        await router.push({ name: 'Dashboard' });
      } catch (error) {
        handleError(error);
      }
    }

    async function register() {
      if (!checkInput()) return;
      try {
        await authProvider.signUp(username.value, password.value);
      } catch (error) {
        handleError(error);
        return;
      }
      try {
        await authProvider.signIn(username.value, password.value);
        await router.push({ name: 'Dashboard' });
      } catch (error) {
        handleError(error);
      }
    }

    watchEffect(() => {
      if (username.value) {
        emailError.value = '';
      }
      if (password.value) {
        passwordError.value = '';
      }
    });
    return {
      username,
      password,
      login,
      emailError,
      passwordError,
      register,
      notConfirmed,
      myMessages,
    };
  },
};
</script>
