<template>
  <div class="flex flex-col overflow-auto">
    <span class="font-bold self-center text-xl">Select Date and Time</span>
    <DaySelect
      :inputSelectedDate="selectedDate"
      @update:selectedDate="logSelectedDate($event)"
      :show-selection="true"
      :show-selected-date="false"
    ></DaySelect>
    <div class="flex flex-col mt-5 overflow-auto h-full items-center">
      <div @click="selectedIndex = index"
           class="w-11/12 h-14 shadow flex justify-between items-center rounded-full pl-5 mb-3 cursor-pointer flex-shrink-0"
           v-for="(recommendation, index) in recommendations" :key="index">
        <span :class="{'text-green-700': selectedIndex === index}">{{ toISOString(recommendation) }}</span>
        <div v-if="index === selectedIndex" class="h-full w-16 rounded-full shadow flex">
          <Icon class="text-green-700 m-auto self-center w-6 h-6" icon-name="check"></Icon>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import DaySelect from "@/components/shared-components/DaySelect.vue";
import {MyDate} from "~/shared-modules/DateTime/MyDate";
import {ref, watchEffect} from "@vue/composition-api";
import {TimeRange} from "~/shared-modules/DateTime/TimeRange";
import Icon from "@/components/shared-components/Icon.vue";
import {reservation} from '@/pages/_company/_calendar/index.vue'
import {nextClick, setValidAndMoveOn, totalDuration} from "~/pages/_company/_calendar/index.vue";

export default {
  components: {DaySelect, Icon},
  props: {
    calendar: {
      required: true
    },
    url: {
      type: String,
      required: true
    },
    companyName: {
      type: String,
      required: true
    },
    startClick: {
      type: Number,
      required: true
    }
  },
  emits: ['selectedTimeRange', 'valid'],
  setup(props: any, {emit}: any) {


    const selectedDate = ref(MyDate.today());
    const selectedIndex = ref<number>(-1)

    const recommendations = ref<Array<TimeRange>>(new Array<TimeRange>(0))

    function logSelectedDate(event: MyDate) {
      selectedDate.value = event;
      updateRecommendations()
    }

    watchEffect(() => {
      reservation.value.timeRange = recommendations.value[selectedIndex.value];
      reservation.value.day = selectedDate.value;
    });

    async function updateRecommendations() {
      const recommendationsRequest =
        await fetch(`${props.url}/public/companies/${props.companyName}/${props.calendar.route}` +
          `/reservations?dateString=${selectedDate.value.toISOString()}&length=${totalDuration.value}`)
      const recommendationsResponse = await recommendationsRequest.json()
      recommendations.value = []
      selectedIndex.value = -1;
      for (const recommendation of recommendationsResponse)
        recommendations.value.push(TimeRange.fromServerResponse(recommendation))
    }

    updateRecommendations()

    function toISOString(timeRange: TimeRange) {
      return `${timeRange.startTime?.getLocaleString('de')} - ${timeRange.endTime?.getLocaleString('de')}`
    }

    function validate() {
      return selectedIndex.value > -1
    }

    setValidAndMoveOn(props, emit, nextClick, validate)
    return {
      selectedDate,
      today: MyDate.fromString("2021-03-01"),
      logSelectedDate,
      recommendations,
      toISOString,
      selectedIndex
    };
  },
};
</script>
