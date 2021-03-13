<template>
  <div
    :style="{ height: containerHeight + 'px' }"
    class="relative w-full max-w-md overflow-auto"
  >
    <!--    full hour lines -->
    <div
      v-for="n in 25"
      :key="n"
      :style="{ top: (n - 1) * hourMargin + 'px' }"
      class="absolute w-full bg-gray-200 h-0.5"
    ></div>
    <!--    full hour strings-->
    <div class="absolute w-full">
      <span
        v-for="n in 25"
        :key="n"
        class="absolute text-gray-400"
        :style="{ top: (n - 1) * hourMargin + 'px' }"
      >
        {{ toHourString(n - 1) }}</span
      >
    </div>
    <!--    half hour lines -->
    <div
      v-for="n in 24"
      :key="n"
      :style="{ top: (n - 1) * hourMargin + hourMargin / 2 + 'px' }"
      class="absolute w-full border border-dashed h-0.5"
    ></div>
    <div
      v-for="reservation in shownReservations"
      :key="reservation.id"
      class="absolute bg-gray-200 bg-opacity-60 w-10/12 max-w-md rounded flex"
      :style="{
        top: timeToPixel(reservation.timeRange.startTime) + 'px',
        height: reservation.timeRange.duration * minuteMargin + 'px',
        right: 0
      }"
    >
      <div class="self-center flex justify-between w-full px-4">
        <span>
          {{ reservation.toLocalString(locale) }}
        </span>
        <span>
          {{ reservation.personalInformation._name }}
        </span>
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import { MyDate } from '@/models/MyDate';
import { computed, ref } from "vue";
import { Time } from '@/models/Time';
import { PersonalInformation, Reservation } from '@/models/Reservation';
import { ReservationService } from '@/services/ReservationService';
export default {
  props: {
    selectedDate: {
      type: MyDate,
      required: true
    },
    containerHeight: {
      type: Number,
      required: true
    },
    locale: {
      type: String,
      required: true
    },
    route: {
      type: String,
      required: true
    }
  },
  setup(props: any) {
    const hourMargin = computed(() => {
      return props.containerHeight / 25;
    });
    const minuteMargin = computed(() => {
      return props.containerHeight / (25 * 60);
    });
    function toHourString(hours: number) {
      const time = new Time(hours, 0);
      return time.getLocaleString(props.locale);
    }
    function timeToPixel(time: Time) {
      return time.getTotalMinutes() * minuteMargin.value;
    }

    const shownReservations = ref<Reservation[]>([]);
    const reservationService = new ReservationService(props.route);
    reservationService
      .retrieveReservations(props.selectedDate)
      .then(reservations => {
        shownReservations.value = reservations;
        console.log(shownReservations.value);
      });
    console.log(shownReservations.value);

    return {
      hourMargin,
      minuteMargin,
      shownReservations,
      toHourString,
      timeToPixel
    };
  }
};
</script>
