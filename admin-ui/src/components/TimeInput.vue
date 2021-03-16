<template>
  <div>
    <span class="font-bold">{{ labelText }}</span>
    <div class="inline-flex mt-3 ml-2">
      <input
        ref="hourInput"
        class="w-10 h-10 none shadow rounded text-center"
        placeholder="h"
        type="text"
        v-model="hours"
      /><span class="ml-1 self-center">:</span>
      <input
        v-model="minutes"
        ref="minuteInput"
        class="w-10 shadow rounded text-center ml-2"
        type="number"
        placeholder="m"
      />
    </div>
  </div>
</template>

<script lang="ts">
import { ref, watch, watchEffect } from 'vue';
import { Time } from '@share/DateTime/Time';
import { useModelWrapper } from '@/ModelWrapper';
export default {
  props: {
    labelText: {
      type: String,
      required: true,
    },
    modelValue: {
      type: Time,
      required: true,
    },
  },
  emits: ['update:modelValue'],
  setup(props, context) {
    const hours = ref<string>('');
    const minutes = ref<string>('');
    const hourInput = ref(null);
    const minuteInput = ref(null);
    const time = useModelWrapper(props, context.emit);
    console.log('time', time.value);
    watch(hours, (currHours, prevHours) => {
      if (currHours.length == 2) {
        // minuteInput.value.focus();
      }
      let i = currHours.length;
      while (i--) {
        if (isNaN(Number(currHours.charAt(i)))) {
          hours.value = prevHours;
        }
      }
      time.value.hours = hours.value;
    });
    watch(minutes, (currMinutes, prevMinutes) => {
      if (currMinutes.length == 2) {
        // minuteInput.value.focus();
      }
      let i = currMinutes.length;
      while (i--) {
        if (isNaN(Number(currMinutes.charAt(i)))) {
          hours.value = currMinutes;
        }
      }
      time.value.minutes = minutes.value;
    });
    return {
      hours,
      minutes,
      hourInput,
      time,
    };
  },
};
</script>

<style>
/* Chrome, Safari, Edge, Opera */
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

/* Firefox */
input[type='number'] {
  -moz-appearance: textfield;
}
</style>
