<template>
  <Card class="flex flex-col h-40">
    <router-link :to="'/calendars/' + calendar.route + '/workslots'">
      <div class="flex flex-row justify-between mt-2.5 px-4">
        <span class="font-semibold">{{ calendar.calendarName }}</span>
        <Icon v-if="calendar.published" icon-name="eye"></Icon>
        <Icon v-if="!calendar.published" icon-name="eye-of"></Icon>
      </div>
      <div class="text-sm flex flex-col ml-5 mt-3">
        <span class="font-light">created at: {{ calendar.createdAt }}</span>
        <span class="font-light"
          >number of entries: {{ calendar.numberOfEntries }}</span
        >
      </div>
    </router-link>
    <div class="flex flex-row-reverse px-2 mb-2">
      <Button v-if="calendar.published" primary class="font-bold"
        >Unpublish</Button
      >
      <Button v-if="!calendar.published" primary class="font-bold"
        >Publish</Button
      >
    </div>
  </Card>
</template>
<script lang="ts">
import CalendarModel from '../models/CalendarModel';
import Button from './Button.vue';
import Card from './Card.vue';
import Icon from './Icon.vue';
export default {
  components: {
    Button,
    Icon,
    Card
  },
  props: {
    calendar: {
      type: CalendarModel,
      required: true
    }
  }
};
</script>
