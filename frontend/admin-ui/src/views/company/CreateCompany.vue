<template>
  <div class="flex flex-col w-full h-screen justify-center align-middle">
    <LabelInput class="w-10/12 self-center" type="text" placeholder="Company Name" v-model="companyName" :error-text="errorText">
      Enter Company Name
    </LabelInput>
    <Button primary class="mt-6 w-36 h-12 self-center" @click="submit">
      <span class="font-bold">Submit</span>
    </Button>
  </div>
</template>

<script lang="ts">
import LabelInput from '@/components/LabelInput.vue';
import { ref } from 'vue';
import Button from '@/components/Button.vue';
import { authProvider } from '@/config/auth-config';
import { url } from '@/config/url-config';
import { CompanyService } from '@share/services/CompanyService';
import { useRouter } from 'vue-router';
export default {
  name: 'CreateCompany',
  components: { Button, LabelInput },
  setup() {
    const companyName = ref('');
    const companyService = new CompanyService(url, authProvider);
    const errorText = ref('');
    const router = useRouter();
    async function submit() {
      try {
        console.log(companyName.value);
        await companyService.createCompany(companyName.value);
        await router.push({ name: 'Dashboard' });
      } catch (e) {
        if (e.message === 'no-company-name') {
          errorText.value = 'Company Name Cannot Be Empty';
        }
      }
    }
    return {
      errorText,
      companyName,
      submit
    };
  }
};
</script>

<style scoped></style>
