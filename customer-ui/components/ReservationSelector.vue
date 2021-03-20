<template>
  <div class="flex flex-col">
    <span class="font-bold self-center text-xl">Select Date and Time</span>
    <DaySelect
      :modelValue="selectedDate"
      @update:modelValue="selectedDate"
      update:ModelValue="selectedDate"
    ></DaySelect>
  </div>
</template>

<script lang="ts">
import DaySelect from "@/components/shared-components/DaySelect.vue";
import { MyDate } from "~/shared-modules/DateTime/MyDate";
import { ref } from "@vue/composition-api";

export default {
  components: { DaySelect },
  setup() {
    const selectedDate = ref(MyDate.today());
    return {
      selectedDate,
    };
  },
};
</script>
