<template>
  <span class="overflow-auto">
    <span class="font-bold text-lg mt-5 self-start">Choose a Service</span>
    <div class="flex flex-col mt-5 space-y-6 px-1">
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
    <div class="flex flex-col mt-3 space-y-6 overflow-auto">
      <div class="flex flex-col space-y-6 overflow-auto px-1 pb-1" v-if="selectedServices.length > 0">
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
      <span class="self-center mt-5 opacity-50" v-else
        >Please Select a Service!</span
      >
    </div>
    <div class="bottom-10 w-10/12 flex justify-between mx-auto mt-5">
      <span class="font-bold">Total Duration: </span>
      <span class="font-bold">{{ totalDuration }} </span>
    </div>
  </span>
</template>
<script lang="ts">
import ListItem from "@/components/ListItem.vue";
import Button from "@/components/shared-components/Button.vue";
import {
  reservation,
  totalDuration,
  nextClick
} from "@/pages/_company/_calendar/index.vue";
import {ref, watchEffect} from "@vue/composition-api";
import {setValidAndMoveOn} from "~/pages/_company/_calendar/index.vue";
export default {
  props: {
    calendar: {
      required: true,
    },
    startClick: {
      type: Number,
      required: true
    }
  },
  emits: ["valid"],
  components: { ListItem, Button },
  setup(props: any, {emit}: any) {
    function removeService(index: number) {
      reservation.value.services.splice(index, 1);
    }
    function validate() {
      return reservation.value.services.length > 0;
    }

    setValidAndMoveOn(props, emit, nextClick, validate);
    return { removeService, totalDuration, selectedServices: reservation.value.services };
  },
};
</script>
