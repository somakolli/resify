<template>
  <div class="select-none">
    <div class="w-max flex self-center m-auto cursor-pointer">
      <Icon
        @click="addToSelectedDate(-1)"
        class="mr-3 w-5 h-5"
        icon-name="arrow-left"
      ></Icon>
      <span class="cursor-pointer" @click="showSelection = !showSelection"
        >{{ dayName }} {{ selectedDate.day }}. {{ monthName }}</span
      >
      <Icon
        @click="addToSelectedDate(1)"
        class="ml-3 w-5 h-5"
        icon-name="arrow-right"
      ></Icon>
    </div>
    <div v-if="showSelection" class="flex-col flex">
      <div class="w-max self-center flex">
        <span>{{ shownDate.getFullYear() }}</span>
      </div>
      <div class="w-full self-center flex justify-between">
        <Icon
          @click="addMonth(-1)"
          class="w-5 h-5 cursor-pointer"
          icon-name="arrow-left"
        ></Icon>
        <span class="w-max self-center">{{ selectedMonthName }}</span>
        <Icon
          @click="addMonth(1)"
          class="w-5 h-5 cursor-pointer"
          icon-name="arrow-right"
        ></Icon>
      </div>
      <div class="mt-5">
        <div class="max-w-md grid grid-cols-7 gap-3">
          <span class="m-auto" v-for="dayName in dayNames" :key="dayName">
            {{ dayName }}
          </span>
          <span
            @click="
              updateSelectedDate(
                shownDate.getFullYear(),
                shownDate.getMonth(),
                n + previousMonthNumberOfDays - firstDay
              )
            "
            class="text-gray-400 hover-hover:hover:bg-gray-200"
            v-for="n in firstDay"
            :key="'before-' + n"
            :class="itemClass"
          >
            {{ n + previousMonthNumberOfDays - firstDay }}
          </span>
          <span
            @click="
              updateSelectedDate(
                shownDate.getFullYear(),
                shownDate.getMonth(),
                n
              )
            "
            :class="[
              {
                'bg-green-200': isSelectedDate(n),
                'hover:bg-gray-200': !isSelectedDate(n),
              },
              itemClass,
            ]"
            v-for="n in numberOfDays"
            :key="n"
          >
            {{ n }}
          </span>
          <span
            class="text-gray-400 hover-hover:hover:bg-gray-200"
            :class="itemClass"
            v-for="n in 6 - lastDay"
            :key="'after-' + n"
            @click="
              updateSelectedDate(
                shownDate.getFullYear(),
                shownDate.getMonth(),
                n
              )
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
import { ref, computed, watchEffect } from "@vue/composition-api";
import { MyDate } from "@/shared-modules/DateTime/MyDate";
export default {
  components: { Icon },
  props: {
    selectedDate: {
      type: MyDate,
      required: true,
    },
    locale: {
      type: String,
      default: "us-en",
    },
  },
  emits: ["update:selectedDate"],
  setup(props: any, context: any) {
    const dayNames = CalendarHelper.getDayNames(props.locale, "short");
    const shownDate = ref(
      new Date(
        props.selectedDate.year,
        props.selectedDate.month,
        props.selectedDate.day
      )
    );

    const monthName = computed(() => {
      return CalendarHelper.getMonthName(
        props.selectedDate.toDate(),
        props.locale,
        "long"
      );
    });
    const selectedMonthName = computed(() => {
      return CalendarHelper.getMonthName(shownDate.value, props.locale, "long");
    });
    const firstDay = computed(() => {
      return CalendarHelper.getFirstDay(shownDate.value);
    });
    const lastDay = computed(() => {
      return CalendarHelper.getLastDay(shownDate.value);
    });
    const numberOfDays = computed(() => {
      return CalendarHelper.getNumberOfDays(shownDate.value);
    });
    const dayName = computed(() => {
      return CalendarHelper.getDayName(
        props.selectedDate.toDate(),
        props.locale,
        "short"
      );
    });
    function addMonth(monthsToAdd: number) {
      shownDate.value = CalendarHelper.addMonths(shownDate.value, monthsToAdd);
      console.log(shownDate);
    }
    function updateSelectedDate(year: number, month: number, day: number) {
      context.emit("update:selectedDate", new MyDate(year, month, day));
    }
    function addToSelectedDate(value: number) {
      context.emit("update:selectedDate", props.selectedDate.addDay(value));
    }
    const previousMonthNumberOfDays = computed(() => {
      return CalendarHelper.getNumberOfDays(
        new Date(shownDate.value.getFullYear(), shownDate.value.getMonth(), 1)
      );
    });
    function updateShownDate(year: number, month: number, day: number) {
      shownDate.value = new Date(year, month, day);
    }
    function isSelectedDate(day: number): boolean {
      return (
        props.selectedDate.year === shownDate.value.getFullYear() &&
        props.selectedDate.month === shownDate.value.getMonth() &&
        props.selectedDate.day === day
      );
    }
    return {
      dayNames,
      monthName,
      selectedMonthName,
      firstDay,
      addMonth,
      numberOfDays,
      dayName,
      shownDate,
      updateSelectedDate,
      addToSelectedDate,
      previousMonthNumberOfDays,
      lastDay,
      isSelectedDate,
      updateShownDate,
    };
  },
  methods: {},
  watch: {
    selectedDate: function (val: any) {},
  },
  data() {
    return {
      showSelection: false,
      itemClass: "m-auto cursor-pointer rounded-full p-2 w-10 text-center",
    };
  },
};
</script>
