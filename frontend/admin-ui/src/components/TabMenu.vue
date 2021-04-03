<template>
  <div>
    <header class="w-full bg-gray-50 flex justify-center pt-3">
      <nav class="w-11/12 self-center justify-between flex h-full">
        <div
          v-for="(tab, index) in tabs"
          :key="tab"
          :to="{ name: tab.routerLink, params: { name: calendarRoute } }"
          class="w-4/12 relative inline-flex cursor-pointer hover-hover:hover:bg-white hover-hover:hover:bg-opacity-70"
          @click="makeActive(index)"
        >
          <div
            class="w-full h-full flex items-center justify-center text-center rounded-t-md px-3 py-2"
            :class="{ 'bg-white': tab.active }"
          >
            {{ tab.name }}
          </div>
          <div v-if="tab.active" class="absolute h-full w-full">
            <div class="h-full w-2 relative float-left -ml-2 bg-white">
              <div class="w-full h-full bg-gray-50 rounded-br-md"></div>
            </div>
            <div class="bg-white h-full w-2 relative float-right -mr-2">
              <div class="w-full h-full bg-gray-50 rounded-bl-md"></div>
            </div>
          </div>
        </div>
      </nav>
    </header>
    <router-view></router-view>
  </div>
</template>
<script lang="ts">
import { ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

export default {
  components: {},
  props: {
    calendarRoute: {
      type: String,
      required: true,
    },
  },
  setup() {
    const router = useRouter();
    const route = useRoute();
    const tabs = ref([
      {
        name: 'Work slots',
        active: false,
        routerLink: 'Calendars/Calendar/WorkSlots',
      },
      {
        name: 'Services',
        active: false,
        routerLink: 'Calendars/Calendar/Services',
      },
      {
        name: 'Reservation',
        active: true,
        routerLink: 'Calendars/Calendar/Reservation',
      },
    ]);

    watch(
      () => route.name,
      async (newRouteName) => {
        tabs.value.forEach((tab) => {
          tab.active = newRouteName === tab.routerLink;
        });
      }
    );

    tabs.value.forEach((tab) => {
      tab.active = route.name === tab.routerLink;
    });

    function makeActive(index: number) {
      tabs.value[index].active = true;
      tabs.value.forEach((tab, tabIndex) => {
        tab.active = index === tabIndex;
        if (tab.active) {
          router.push({
            name: tab.routerLink,
            params: { name: 'jonas-calendar' },
          });
        }
      });
    }
    return {
      tabs,
      makeActive,
    };
  },
};
</script>
