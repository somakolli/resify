<template>
  <div class="w-full">
    <div class="font-bold text-xl w-full text-center underline">Summary</div>
    <div class="flex flex-col">
      <div class="flex flex-col">
        <div class="flex flex-row justify-between mt-5">
          <span class="font-bold text-xl">Services</span>
          <Icon v-if="reservationState!==ReservationState.Confirmed"
                @click="reservationState = ReservationState.Service"
                icon-name="pencil" class="w-6 h-6"></Icon>
        </div>
        <div class="flex flex-col w-full self-center">
          <ListItem class="mt-3" v-for="(service, index) in reservation.services" :duration="service.duration"
                    :main-content="service.name" :key="index"></ListItem>
        </div>
      </div>
      <div>
        <div class="flex flex-row justify-between mt-10">
          <span class="font-bold text-xl">Date</span>
          <Icon v-if="reservationState!==ReservationState.Confirmed"
                @click="reservationState = ReservationState.Date" icon-name="pencil" class="w-6 h-6"></Icon>
        </div>
        <div class="mt-5 text-center font-bold text-xl">
          {{ reservation.day.dayNameLong() }}<br>
          {{ reservation.day.formatLong() }}<br>
          {{ reservation.timeRange.toLocaleString() }}
        </div>
      </div>
      <div>
        <div class="flex flex-row justify-between mt-10">
          <span class="font-bold text-xl">Contact Information</span>
          <Icon v-if="reservationState!==ReservationState.Confirmed"
            @click="reservationState = ReservationState.PersonalInformation" icon-name="pencil" class="w-6 h-6"></Icon>
        </div>
        <div class="flex font-bold flex-col text-center text-xl mt-5">
          <span v-for="information in reservation.personalInformation">{{information}}</span>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import {reservation, reservationState, ReservationState} from "@/pages/_company/_calendar/index.vue"
import Icon from "@/components/shared-components/Icon.vue"
import ListItem from "@/components/ListItem.vue";

export default {
  components: {Icon, ListItem},
  setup() {
    return {
      reservation,
      reservationState,
      ReservationState
    }
  }
}
</script>
