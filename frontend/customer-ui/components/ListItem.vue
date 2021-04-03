<template>
  <div
    @click="$emit('click')"
    class="flex w-full bg-white h-10 shadow rounded-full justify-between items-center pl-5"
  >
    <div class="overflow-auto font-semibold">{{ mainContent }}</div>
    <div
      class="flex justify-center items-center bg-white shadow rounded-full min-w-16 px-2 h-full"
    >
      <span class="mr-1 font-semibold">{{ duration }}m</span>
      <div
        v-if="iconName"
        class="shadow rounded-full h-full w-10 -mr-2 flex items-center justify-center"
      >
        <Icon class="w-8 h-8 text-green-700" :icon-name="iconName"></Icon>
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import Icon from "./shared-components/Icon.vue";
import { computed } from "@nuxtjs/composition-api";
export default {
  components: { Icon },
  emits: ["click"],
  props: {
    sideContent: {
      type: String,
      required: false,
    },
    mainContent: {
      type: String,
      required: true,
    },
    iconName: {
      type: String,
      required: false,
    },
    showIcon: {
      type: Boolean,
      required: false,
    },
    duration: {
      type: Number,
      required: true,
    },
  },
  setup(props: any) {
    const mainContentTrimmed = computed(() => {
      if (props.mainContent.length > 16) {
        return props.mainContent.slice(0, 14) + " ...";
      } else {
        return props.mainContent;
      }
    });
    return {
      mainContentTrimmed,
    };
  },
};
</script>
