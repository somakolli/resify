<template>
  <div>
    <div
      class="xl:hidden flex justify-between px-4 h-14 bg-white border-b flex-none"
    >
      <Icon
        class="w-6 h-6 self-center"
        @click="$emit('close', $event)"
        icon-name="arrow-left"
      ></Icon>
      <span class="w-max self-center font-bold">{{
        calendar.calendarName
      }}</span>
      <div class="w-6 h-6 invisible"></div>
    </div>
    <TabMenu
      class="flex flex-col overflow-auto"
      calendar-route="calendar.id"
    ></TabMenu>
  </div>
</template>
<script>
import Icon from '@components/Icon.vue';
import TabMenu from '../../../components/TabMenu.vue';
import CalendarModel from '@share/models/CalendarModel';
export default {
  props: {
    calendar: {
      type: CalendarModel,
      required: true,
    },
  },
  components: { TabMenu, Icon },
  emits: ['close'],
};
</script>
