<template>
  <div class="flex flex-col w-full h-screen justify-between items-center">
    <div class="w-10/12 mt-5 flex flex-col items-center">
      <div class="font-bold text-xl">{{ calendar.calendarName }}</div>
      <ServiceSelector
        v-if="reservationState === ReservationState.Service"
        class="w-full mt-5"
        :calendar="calendar"
      ></ServiceSelector>
      <ReservationSelector
        v-else-if="reservationState === ReservationState.Date"
        class="w-full mt-5"
        :calendar="calendar"
        :url="url"
        :companyName="companyName"
      ></ReservationSelector>
      <PersonalInformationEntry
        v-else-if="reservationState === ReservationState.PersonalInformation"
        class="w-full mt-5"
      ></PersonalInformationEntry>
      <Summary
        v-else-if="reservationState === ReservationState.Summary"
        class="w-full mt-5"
      ></Summary>
    </div>
    <div class="w-full flex flex-col items-center mb-5">
      <Button
        v-if="reservationState === ReservationState.Summary"
        primary
        class="w-32 h-10"
        >Confirm</Button
      >
      <Button v-else @click="reservationState++" primary class="w-32 h-10"
        >Next</Button
      >
    </div>
  </div>
</template>

<script lang="ts">
import {computed, ref, watchEffect} from "@vue/composition-api";
import ListItem from "@/components/ListItem.vue";
import Button from "@/components/shared-components/Button.vue";
import ReservationSelector from "@/components/ReservationSelector.vue";
import PersonalInformationEntry from "@/components/PersonalInformationEntry.vue";
import Summary from "@/components/Summary.vue";
import {TimeRange} from "~/shared-modules/DateTime/TimeRange";
export const selectedServices = ref(new Array(0));
export const url = "http://localhost:8080/"
export const totalDuration = computed(() => {
  let returnDuration = 0;
  for (const service of selectedServices.value) {
    returnDuration += service.duration;
  }
  return returnDuration;
});
export const selectedTimeRange = ref<TimeRange>();
enum ReservationState {
  Service = 0,
  Date = 1,
  PersonalInformation = 2,
  Summary = 3,
}
export default {
  components: {
    ListItem,
    Button,
    ReservationSelector,
    PersonalInformationEntry,
    Summary,
  },

  setup() {
    const reservationState = ref(ReservationState.Service);
    const numberOfSteps = 4;
    return {
      selectedServices,
      totalDuration,
      reservationState,
      ReservationState,
      numberOfSteps,
      url,
      selectedTimeRange
    };
  },
  // this gets executed on the server
  async asyncData({ params, $http }: any) {
    //TODO: get calendar from previous request
    const calendar = await $http.$get(
      `public/${params.company}/${params.calendar}`
    );
    return {
      calendar,
      companyName: params.company
    };
  },
};
</script>
