<template>
  <div class="flex flex-col w-full h-screen justify-between items-center">
    <div class="w-10/12 mt-5 flex flex-col items-center">
      <div class="font-bold text-xl">{{ calendar.calendarName }}</div>
      <ServiceSelector
        class="w-full mt-5"
        :calendar="calendar"
      ></ServiceSelector>
    </div>
    <div class="w-full flex flex-col items-center mb-5">
      <Button primary class="w-32 h-10">Next</Button>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, ref } from "@vue/composition-api";
import ListItem from "@/components/ListItem.vue";
import Button from "@/components/shared-components/Button.vue";

export const selectedServices = ref(new Array(0));
export const totalDuration = computed(() => {
  let returnDuration = 0;
  for (const service of selectedServices.value) {
    returnDuration += service.duration;
  }
  return returnDuration;
});

export default {
  components: { ListItem, Button },

  setup() {
    return { selectedServices, totalDuration };
  },
  async asyncData({ params, $http }: any) {
    //TODO: get calendar from previous request
    const calendar = await $http.$get(
      `public/${params.company}/${params.calendar}`
    );
    return {
      calendar,
    };
  },
};
</script>
