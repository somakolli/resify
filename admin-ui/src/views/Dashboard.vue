<template>
  <div class="flex">
    <div
      id="nav"
      :class="navVisibility"
      class="sm:flex w-16 bg-gray-50 h-screen min-w-16"
    >
      <div class="w-full flex flex-col">
        <router-link
          v-for="sideBarIcon in sideBarIcons"
          :key="sideBarIcon.iconName"
          v-slot="{ isActive }"
          :to="'/' + sideBarIcon.route"
          class="w-full relative"
        >
          <div
            v-if="isActive"
            class="absolute w-full h-2 bg-white float-left -mt-2"
          ></div>
          <div
            v-if="isActive"
            class="absolute w-full h-2 bg-gray-50 rounded-br-lg float-left -mt-2"
          ></div>
          <div
            :class="{ 'bg-white': isActive }"
            class="w-full h-16 flex justify-center align-middle"
          >
            <Icon
              class="flex transition-width duration-300 ease-in-out w-7 hover:w-8"
              :icon-name="sideBarIcon.iconName"
            ></Icon>
          </div>
          <div
            v-if="isActive"
            class="absolute w-full h-2 bg-white float-left"
          ></div>
          <div
            v-if="isActive"
            class="absolute w-full h-2 bg-gray-50 rounded-tr-lg float-left"
          ></div>
        </router-link>
      </div>
    </div>
    <div class="w-full h-screen">
      <router-view />
    </div>
  </div>
</template>
<script lang="ts">
import { useRoute, useRouter } from 'vue-router';

import Icon from '../components/Icon.vue';
import { authProvider } from '@/config/auth-config';
export default {
  components: { Icon },
  setup() {
    const route = useRoute();
    const router = useRouter();
    if (route.meta.requiresAuth) {
      if (!authProvider.isSignedIn) {
        router.push({ name: 'Login' });
      }
    }
  },
  data() {
    return {
      sideBarIcons: [
        {
          iconName: 'calendar',
          route: 'calendars',
        },
        {
          iconName: 'stats',
          route: 'stats',
        },
        {
          iconName: 'logout',
          route: 'logout',
        },
      ],
      navVisibility: 'flex',
      windowLargeEnough: true,
    };
  },
  watch: {
    $route(to) {
      if (to.name.includes('Calendars/Calendar')) {
        this.navVisibility = 'hidden';
      } else {
        this.navVisibility = 'flex';
      }
    },
  },
};
</script>
