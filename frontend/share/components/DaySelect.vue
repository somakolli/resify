<template>
  <div class="select-none max-w-md self-center">
    <div v-if="showSelectedDate" class="w-max flex self-center m-auto cursor-pointer">
      <Icon
          @click="selectedDate.addDay(-1)"
          class="mr-3 w-5 h-5"
          icon-name="arrow-left"
      ></Icon>
      <span class="cursor-pointer" @click="showSelection = !showSelection"
      >{{ selectedDate.dayNameShort() }} {{ selectedDate.day }}. {{ selectedDate.monthNameLong() }}</span
      >
      <Icon
          @click="selectedDate = selectedDate.addDay(1)"
          class="ml-3 w-5 h-5"
          icon-name="arrow-right"
      ></Icon>
    </div>
    <div v-if="showSelection" class="flex-col flex self-center">
      <div class="w-max self-center flex">
        <span>{{ shownDate.year }}</span>
      </div>
      <div class="w-full self-center flex justify-between">
        <Icon
            @click="shownDate.addMonth(-1)"
            class="w-5 h-5 cursor-pointer"
            icon-name="arrow-left"
        ></Icon>
        <span class="w-max self-center text-xl">{{ shownDate.monthNameLong() }}</span>
        <Icon
            @click="shownDate.addMonth(1); "
            class="w-5 h-5 cursor-pointer"
            icon-name="arrow-right"
        ></Icon>
      </div>
      <div class="mt-5 self-center">
        <div class="max-w-md grid grid-cols-7 gap-3">
          <span class="m-auto" v-for="dayName in dayNames" :key="dayName">
            {{ dayName }}
          </span>
          <span
              v-for="n in shownDate.firstDay()"
              @click="
              selectedDate = shownDate.firstDateOfMonth().addDay(shownDate.firstDay() - n - 1)
            "
              class="text-gray-400 hover:bg-gray-200"
              :key="'before-' + n + 1"
              :class="itemClass"
          >
            {{ n + previousMonthNumberOfDays - shownDate.firstDay() }}
          </span>
          <span
              @click="
              selectedDate = shownDate.firstDateOfMonth().addDay(n - 1)
            "
              :class="[
              {
                'bg-green-200': isSelectedDate(n),
                'hover:bg-gray-200': !isSelectedDate(n),
              },
              itemClass,
            ]"
              v-for="n in shownDate.numberOfDays()"
              :key="n"
          >
            {{ n }}
          </span>
          <span
              class="text-gray-400 hover:bg-gray-200"
              :class="itemClass"
              v-for="n in 6 - shownDate.lastDay()"
              :key="'after-' + n"
              @click="
              selectedDate = shownDate.lastDateOfMonth().addDay(n)
            "
          >{{ n }}</span
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import CalendarHelper from "@/shared-modules/helpers/CalendarHelper";
import Icon from "@/components/shared-components/Icon.vue";
import { ref, computed } from "@vue/composition-api";
import { MyDate } from "@/shared-modules/DateTime/MyDate";
export default {
  components: { Icon },
  props: {
    inputSelectedDate: {
      type: MyDate,
      required: true,
    },
    locale: {
      type: String,
      default: "us-en",
    },
    showSelection: {
      type: Boolean,
      default: false
    },
    showSelectedDate: {
      type: Boolean,
      default: true
    }
  },
  emits: ["update:selectedDate"],
  setup(props: any, context: any) {
    const dayNames = CalendarHelper.getDayNames(props.locale, "short");
    const shownDate = ref(
        new MyDate(props.inputSelectedDate.year, props.inputSelectedDate.month, props.inputSelectedDate.day)
    );

    const selectedDate = computed({
      get: () => props.inputSelectedDate,
      set: (val: MyDate) => {
        context.emit('update:selectedDate', val)
        shownDate.value = new MyDate(val.year, val.month, val.day);
      }
    })

    function updateSelectedDate(year: number, month: number, day: number) {
      context.emit("update:selectedDate", new MyDate(year, month, day));
    }
    function addToSelectedDate(value: number) {
      context.emit("update:selectedDate", props.selectedDate.addDay(value));
    }
    const previousMonthNumberOfDays = computed(() => {
      return CalendarHelper.getNumberOfDays(
          new Date(shownDate.value.year, shownDate.value.month - 1, 1)
      );
    });
    function updateShownDate(year: number, month: number, day: number) {
      shownDate.value = new MyDate(year, month, day);
    }
    function isSelectedDate(day: number): boolean {
      return (
          selectedDate.value.month === shownDate.value.month &&
          selectedDate.value.year === shownDate.value.year &&
          selectedDate.value.day === day
      );
    }
    return {
      dayNames,
      shownDate,
      updateSelectedDate,
      addToSelectedDate,
      previousMonthNumberOfDays,
      isSelectedDate,
      updateShownDate,
      selectedDate,
      MyDate
    };
  },
  data() {
    return {
      itemClass: "m-auto cursor-pointer rounded-full p-2 w-10 text-center",
    };
  },
};
</script>

