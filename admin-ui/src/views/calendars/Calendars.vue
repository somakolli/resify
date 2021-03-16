<template>
  <div class="flex">
    <div
      :class="calendarVisibility"
      class="md:max-w-sm w-full border-r border-gray-200 h-screen sm:block md:min-w-sm"
    >
      <div class="flex w-full justify-center">
        <div class="flex flex-row w-11/12 justify-between mt-5">
          <span class="font-bold">Calendars</span>
          <Icon
            class="w-6 h-6 cursor-pointer"
            icon-name="addCalendar"
            @click="addCalendar = !addCalendar"
          ></Icon>
        </div>
      </div>
      <div class="flex flex-col w-full content-center mt-5 px-5">
        <Calendar
          v-for="calendar in data"
          :key="calendar.route"
          class="w-full mt-5 first:mt-0"
          :calendar="calendar"
        ></Calendar>
      </div>
    </div>
    <PopUpModal
      v-if="addCalendar"
      @close="addCalendar = !addCalendar"
      confirm-button-text="create"
      @confirm="addCalendarRequest"
      title="Create new Calendar"
    >
      <LabelInput v-model="newCalendarName" placeholder="Calendar Name">
        Set Calendar
      </LabelInput>
    </PopUpModal>
    <router-view></router-view>
  </div>
</template>
<script lang="ts">
import Calendar from '../../components/Calendar.vue';
import Icon from '../../components/Icon.vue';
import LabelInput from '../../components/LabelInput.vue';
import PopUpModal from '../../components/PopUpModal.vue';
import CalendarModel from '@share/models/CalendarModel';
import { ref } from 'vue';
import { url } from '@/config/url-config';
import { CalendarService } from '@share/services/CalendarService';
import { authProvider } from '@/config/auth-config';
export default {
  components: { LabelInput, PopUpModal, Icon, Calendar },
  setup() {
    const newCalendarName = ref('');
    const calendarService = new CalendarService(url, authProvider);
    const addCalendar = ref(false);

    async function addCalendarRequest() {
      await calendarService.createCalendar(newCalendarName.value);
      addCalendar.value = false;
    }
    const data = ref<CalendarModel[]>(new Array<CalendarModel>());

    calendarService
      .getCalendars()
      .then((returnData) => (data.value = returnData));

    return {
      newCalendarName,
      addCalendarRequest,
      data,
      addCalendar,
    };
  },
  data() {
    return {
      showCalendars: true,
    };
  },
  computed: {
    calendarVisibility() {
      if (this.$route.name.includes('Calendars/Calendar')) {
        return 'hidden';
      } else {
        return 'block';
      }
    },
  },
};
</script>
