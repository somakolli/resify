<template>
  <div class="flex flex-col w-full h-screen justify-between items-center">
    <div class="w-10/12 mt-5 flex flex-col items-center">
      <div class="font-bold text-xl">{{ calendar.calendarName }}</div>
      <span class="font-bold text-lg mt-5 self-start">Choose a Service</span>
      <div class="flex flex-col mt-5 space-y-6 w-full">
        <ListItem
          v-for="service in calendar.services"
          :key="service.serviceId"
          :mainContent="service.name"
          iconName="plus"
          @click="selectedServices.push(service)"
          :duration="service.duration"
        >
        </ListItem>
      </div>
      <div class="font-bold mt-6 text-lg self-start">Selected Services</div>
      <div class="flex flex-col mt-3 space-y-6 w-full">
        <div class="flex flex-col space-y-6" v-if="selectedServices.length > 0">
          <ListItem
            v-for="(service, index) in selectedServices"
            :key="index"
            :mainContent="service.name"
            iconName="x"
            @click="removeService(index)"
            :duration="service.duration"
          >
          </ListItem>
        </div>
        <span class="self-center mt-10 opacity-50" v-else
          >Please Select a Service!</span
        >
      </div>
    </div>
    <div class="bottom-10 w-8/12 flex justify-between mx-auto">
      <span class="font-bold">Total Duration: </span>
      <span class="font-bold">{{ totalDuration }} </span>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, ref } from "@vue/composition-api";
import ListItem from "../../../components/ListItem.vue";
export default {
  components: { ListItem },
  setup() {
    const selectedServices = ref(new Array(0));
    console.log(selectedServices.value);
    function removeService(index: number) {
      selectedServices.value.splice(index, 1);
    }

    const totalDuration = computed(() => {
      let returnDuration = 0;
      for (const service of selectedServices.value) {
        returnDuration += service.duration;
      }
      return returnDuration;
    });
    return { selectedServices, removeService, totalDuration };
  },
  async asyncData({ params, $http }: any) {
    //TODO: get calendar from previous request
    const calendar = await $http.$get(
      `public/${params.company}/${params.calendar}`
    );
    console.log(calendar);
    return {
      calendar,
    };
  },
};
</script>
