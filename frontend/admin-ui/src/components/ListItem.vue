<template>
  <div
    class="flex w-full bg-white h-14 shadow rounded-full justify-between items-center pl-5"
  >
    <div class="overflow-auto">{{ mainContent }}</div>
    <div
      class="flex justify-center items-center bg-white shadow rounded-full min-w-16 px-3 h-full"
    >
      <span v-if="!showIcon">{{ sideContent }}</span>
      <Icon v-if="showIcon" class="w-6 h-6" :icon-name="iconName"></Icon>
    </div>
  </div>
</template>
<script lang="ts">
import Icon from '@components/Icon.vue';
import { computed } from 'vue';
export default {
  components: { Icon },
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
  },
  setup(props: any) {
    const mainContentTrimmed = computed(() => {
      if (props.mainContent.length > 16) {
        return props.mainContent.slice(0, 14) + ' ...';
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
