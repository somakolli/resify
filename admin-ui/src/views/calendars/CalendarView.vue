<template>
  <div class="w-full flex">
    <div
      class="flex-col h-screen xl:w-8/12 bg-white w-full"
      :class="calendarVisibleClass"
    >
      <div class="flex flex-col shadow">
        <div
          class="flex items-center justify-between px-4 h-14 w-full bg-white"
        >
          <router-link to="/calendars">
            <Icon class="sm:invisible w-6 h-6" icon-name="arrow-left"></Icon>
          </router-link>
          <div class="font-bold">{{ route }}</div>
          <Icon
            @click="showConfig"
            class="xl:invisible w-6 h-6"
            icon-name="adjustments"
          ></Icon>
        </div>
        <DaySelect
          v-model:selectedDate="selectedDate"
          locale="de"
          class="w-max self-center mb-5"
        ></DaySelect>
      </div>
      <CalendarDay
        :route="route"
        locale="de"
        class="self-center"
        :selected-date="selectedDate"
        :container-height="3000"
      ></CalendarDay>
    </div>
    <Configuration
      v-if="calendar"
      :calendar="calendar"
      @close="hideConfig"
      class="border-l xl:w-4/12 w-full h-screen flex-col flex"
      :class="configVisibleClass"
    ></Configuration>
  </div>
</template>
<script lang="ts">
import Configuration from './configuration/Configuration.vue';
import { MyDate } from '@share/DateTime/MyDate';
import CalendarModel from '@share/models/CalendarModel';
import Icon from '@/components/Icon.vue';
import CalendarDay from '@/components/CalendarDay.vue';
import DaySelect from '@/components/DaySelect.vue';
import { ref } from 'vue';
import { url } from '@/config/url-config';
import { CalendarService } from '@share/services/CalendarService';
import { authProvider } from '@/config/auth-config';

export default {
  components: { CalendarDay, DaySelect, Configuration, Icon },
  setup(props) {
    const calendarService = new CalendarService(url, authProvider);
    const calendar = ref<CalendarModel>();
    const calendarResponse = calendarService.getCalendar(props.route);
    calendar.value = new CalendarModel(calendarResponse);
    return {
      calendar,
    };
  },
  props: {
    route: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      configVisibleClass: 'hidden xl:flex',
      calendarVisibleClass: 'flex',
      months: [
        'Januar',
        'Februar',
        'März',
        'April',
        'Mai',
        'Juni',
        'Juli',
        'August',
        'September',
        'Oktober',
        'November',
        'Dezember',
      ],
      selectedDate: new MyDate(
        new Date(Date.now()).getFullYear(),
        new Date(Date.now()).getMonth(),
        new Date(Date.now()).getDate()
      ),
      selectedMonth: 0,
    };
  },
  methods: {
    showConfig() {
      this.configVisibleClass = 'xl:w-full';
      this.calendarVisibleClass = 'hidden';
    },
    hideConfig() {
      this.configVisibleClass = 'hidden xl:flex';
      this.calendarVisibleClass = 'flex';
    },
  },
};
</script>
