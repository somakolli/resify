<template>
  <div class="flex flex-col overflow-auto">
    <div
      class="h-full flex flex-col w-full flex-1 items-center overflow-auto justify-between"
    >
      <div
        v-for="(workSlotDay, index) in workSlotDays"
        :key="index"
        class="w-10/12 flex flex-col items-center"
      >
        <div class="w-full flex justify-between mt-5">
          <span class="font-bold self-center text-xl">{{
            workSlotDay.name
          }}</span>
          <div class="flex w-16 h-max self-center items-center justify-between">
            <Icon
              @click="editable[index] = !editable[index]"
              icon-name="pencil"
              class="w-6 h-6 cursor-pointer"
            ></Icon>
            <Icon
              @click="
                addWorkSlot = true;
                currentAddingDay = index;
              "
              icon-name="plus"
              class="w-8 h-8 cursor-pointer"
            ></Icon>
          </div>
        </div>
        <div class="grid grid-cols-2 w-11/12 mt-5 gap-7">
          <WorkSlot
            :removable="editable[index]"
            v-for="workSlot in workSlotDay.workSlots"
            :key="workSlot"
            :work-slot="workSlot"
          ></WorkSlot>
        </div>
      </div>
    </div>
    <div class="mt-10 mb-10 w-full">
      <Button
        @click="workSlotService.generateWorkSlots(7)"
        class="h-10 m-auto w-4/12"
        primary
        >Generate Workslots</Button
      >
    </div>
    <PopUpModal
      v-if="addWorkSlot"
      @close="addWorkSlot = false"
      title="Create new Config WorkSlot"
      confirm-button-text="Create"
      @confirm="createWorkSlot(currentAddingDay)"
    >
      <div class="flex flex-col">
        <TimeInput label-text="Start Time" v-model="startTime"></TimeInput>
        <TimeInput
          class="mt-3"
          label-text="End Time"
          v-model="endTime"
        ></TimeInput>
      </div>
    </PopUpModal>
  </div>
</template>
<script lang="ts">
import Icon from '../../../components/Icon.vue';
import { ref } from 'vue';
import WorkSlot from '../../../components/WorkSlot.vue';
import { WorkSlotDay } from '@/models/WorkSlotDay';
import CalendarHelper from '@/helpers/CalendarHelper';
import { localeConfig } from '@/config/locale-config';
import { CalendarService } from '@/services/CalendarService';
import PopUpModal from '@/components/PopUpModal.vue';
import TimeInput from '@/components/TimeInput.vue';
import { Time } from '@/models/Time';
import { WorkSlotService } from '@/services/WorkSlotService';
import { ConfigWorkSlot } from '@/models/WorkSlotModel';
import { TimeRange } from '@/models/TimeRange';
import Button from '@/components/Button.vue';
export default {
  components: { Button, TimeInput, PopUpModal, WorkSlot, Icon },
  props: {
    route: {
      type: String,
      required: true
    }
  },
  setup(props) {
    const addWorkSlot = ref(false);
    const startTime = ref(Time.fromDate(new Date(Date.now())));
    const endTime = ref(Time.fromDate(new Date(Date.now())));
    const workSlotService = new WorkSlotService(props.route);
    const workSlotDays = ref<WorkSlotDay[]>([]);
    const currentAddingDay = ref<number>(0);
    for (const name of CalendarHelper.getDayNames(
      localeConfig.locale,
      'long'
    )) {
      workSlotDays.value.push(new WorkSlotDay(name));
    }
    async function createWorkSlot(day: number) {
      const configWorkSlot = new ConfigWorkSlot();
      configWorkSlot.timeRange = new TimeRange();
      configWorkSlot.timeRange.startTime = startTime.value;
      configWorkSlot.timeRange.endTime = endTime.value;
      configWorkSlot.day = day;
      const newWorkSlot = await workSlotService.createConfigWorkSlot(
        configWorkSlot
      );
      workSlotDays.value[day].workSlots.push(newWorkSlot);
    }
    const calendarService = new CalendarService();
    calendarService.getCalendar(props.route).then(calendar => {
      for (const configWorkSlot of calendar.workSlotConfiguration) {
        const parsedWorkSlot = ConfigWorkSlot.fromServerResponse(
          configWorkSlot
        );
        workSlotDays.value[parsedWorkSlot.day].workSlots.push(parsedWorkSlot);
      }
    });
    const editable = ref(new Array<boolean>(workSlotDays.value.length));
    editable.value.forEach((value, index) => (editable.value[index] = false));
    return {
      workSlotDays,
      editable,
      addWorkSlot,
      startTime,
      endTime,
      createWorkSlot,
      currentAddingDay,
      workSlotService
    };
  }
};
</script>
