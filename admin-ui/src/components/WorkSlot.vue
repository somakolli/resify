<template>
  <div class="relative flex w-full">
    <div class="shadow px-3 rounded py-1.5 text-center w-full">
      {{ workSlot.timeRange.startTime.getLocaleString('de') }} -
      {{ workSlot.timeRange.endTime.getLocaleString('de') }}
    </div>
    <Icon
      v-if="removable"
      icon-name="x-circle"
      class="absolute h-6 w-6 -top-3 -right-3 bg-opacity-60 bg-white rounded-full cursor-pointer"
    ></Icon>
  </div>
</template>
<script lang="ts">
import { ConfigWorkSlot } from '@share/models/WorkSlotModel';
import Icon from './Icon.vue';

export default {
  components: { Icon },
  props: {
    workSlot: {
      type: ConfigWorkSlot,
      required: true,
    },
    removable: {
      type: Boolean,
    },
  },
};
</script>
