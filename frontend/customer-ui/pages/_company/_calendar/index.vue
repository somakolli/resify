<template>
  <div class="flex flex-col w-full h-screen justify-between items-center">
    <div class="w-10/12 mt-5 flex flex-col items-center">
      <div class="font-bold text-xl">{{ calendar.calendarName }}</div>
      <ServiceSelector
        v-if="reservationState === ReservationState.Service"
        class="w-full mt-5"
        :calendar="calendar"
        :startClick="nextClick"
        @valid="reservationState++"
        :ref="ReservationState.Service"
      ></ServiceSelector>
      <ReservationSelector
        v-else-if="reservationState === ReservationState.Date"
        class="w-full mt-5"
        :calendar="calendar"
        :url="url"
        :companyName="companyName"
        :startClick="nextClick"
        @valid="reservationState++"
      ></ReservationSelector>
      <PersonalInformationEntry
        v-else-if="reservationState === ReservationState.PersonalInformation"
        class="w-full mt-5"
        :calendar="calendar"
        :startClick="nextClick"
        @valid="reservationState++"
      ></PersonalInformationEntry>
      <Summary
        v-else-if="reservationState === ReservationState.Summary || ReservationState.Confirmed"
        class="w-full mt-5">
      </Summary>
      <div v-else>Error! State not found!</div>
    </div>
    <div class="w-full flex flex-col items-center mb-5">
      <Button
        v-if="reservationState === ReservationState.Summary"
        primary
        class="w-32 h-10"
        @click="sendReservation()">
        Confirm
      </Button>
      <div v-else-if="reservationState === ReservationState.Confirmed">
        <a :href="'data:text/calendar;charset=utf8,' + icalReservation.value" class="w-32 h-10">
          <Button @click="addToCalendar" class="w-32 h-10">Add To Calendar</Button>
        </a>
      </div>
      <Button v-else @click="nextClick++" primary class="w-32 h-10 my-5">
        Next
      </Button>
    </div>
  </div>
</template>

<script lang="ts">
import {computed, ref} from "@vue/composition-api";
import ListItem from "@/components/ListItem.vue";
import Button from "@/components/shared-components/Button.vue";
import ReservationSelector from "@/components/ReservationSelector.vue";
import PersonalInformationEntry from "@/components/PersonalInformationEntry.vue";
import Summary from "@/components/Summary.vue";
import ServiceSelector from "@/components/ServiceSelector.vue";
import {TimeRange} from "~/shared-modules/DateTime/TimeRange";
import {Reservation} from "~/shared-modules/models/Reservation";
import {watch} from "@nuxtjs/composition-api";
import {HttpHelper} from "~/shared-modules/helpers/HttpHelper";

export const reservation = ref<Reservation>(new Reservation("", new TimeRange(), {}));
export const url = "http://localhost:8080/";
export const totalDuration = computed(() => {
  let returnDuration = 0;
  for (const service of reservation.value.services) {
    returnDuration += service.duration;
  }
  return returnDuration;
});

export function setValidAndMoveOn(props: any, emit: any, nextClick: any, validate: any) {
  let startClick = props.startClick;
  watch(() => nextClick.value, (count) => {
    if (count > startClick && validate())
      emit("valid", true);
    else
      startClick = count;
  })
}

export const nextClick = ref(0);
export const selectedTimeRange = ref<TimeRange>();

export enum ReservationState {
  Service = 0,
  Date = 1,
  PersonalInformation = 2,
  Summary = 3,
  Confirmed = 4
}

export const reservationState = ref(ReservationState.Service);
let companyName = ''
let calendar: any = null
const validState = new Array<boolean>(4).fill(false)
export default {
  components: {
    ServiceSelector,
    ListItem,
    Button,
    ReservationSelector,
    PersonalInformationEntry,
    Summary,
  },

  setup(props: any, test: any) {
    console.log('root', test)

    async function sendReservation() {
      console.log(calendar);
    }
    const icalReservation = ref("");
    const numberOfSteps = 4;
    function addToCalendar() {


    }
    return {
      reservation,
      totalDuration,
      reservationState,
      ReservationState,
      numberOfSteps,
      url,
      selectedTimeRange,
      nextClick,
      icalReservation,
      addToCalendar
    };
  },
  methods: {
    sendReservation: async function () {
      let httpHelper = new HttpHelper();
      // clone object
      const requestReservation = JSON.parse(JSON.stringify(reservation.value))
      requestReservation.personalInformation = JSON.stringify(requestReservation.personalInformation)
      requestReservation.id = null
      try {
        const iCalReservation = await httpHelper.postData(`${url}public/${(this as any).companyName}/${(this as any).calendar.route}/reservations`, requestReservation)
        reservationState.value++
        (this as any).icalReservation = iCalReservation;
      } catch (e) {

     }
    }
  },
  // this gets executed on the server
  async asyncData({params, $http}: any) {
    //TODO: get calendar from previous request
    const url = `public/${params.company}/${params.calendar}`
    calendar = await $http.$get(url);
    companyName = params.company

    return {
      calendar,
      companyName: params.company,
    };
  },
};
</script>
