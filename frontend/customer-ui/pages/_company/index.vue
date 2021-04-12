<template>
  <div class="flex flex-col items-center w-full">
    <Icon class="w-16 h-16 mt-7" iconName="logo"> </Icon>
    <Icon class="h-16 mt-7" iconName="name"> </Icon>
    <span class="font-bold text-lg">Choose a Calendar</span>
    <div class="w-full flex flex-col items-center mt-5">
      <NuxtLink
        no-prefetch
        class="w-10/12 shadow rounded-md flex flex-row justify-between px-3 py-3 font-bold max-w-lg"
        v-for="calendar in calendars"
        :key="calendar.calendarName"
        :to="`${companyName}/${calendar.route}`"

      >
        <Icon iconName="calendar" class="w-6 h-6"></Icon>
        <span>{{ calendar.calendarName }}</span>
        <Icon iconName="arrow-right" class="w-6 h-6"></Icon>
      </NuxtLink>
    </div>
  </div>
</template>
<script lang="ts">
import Icon from "../../components/shared-components/Icon.vue";

export default {
  components: { Icon },
  async asyncData({ params, $http }: any) {
    console.log(`/public/companies/${params.company}`);
    console.log($http)
    const calendars = await $http.$get(`/public/companies/${params.company}`);
    const companyName = params.company;
    return {
      calendars,
      companyName,
    };
  },
};
</script>
