<template>
  <div class="text-gray-800" v-html="svg"></div>
</template>

<script lang="ts">
import { iconRepository } from "./IconRepository";
import { ref } from "vue";
export default {
  name: "Icon",
  props: {
    iconName: {
      type: String,
      required: true,
    },
  },
  setup(props: any) {
    const svg = ref("");
    svg.value = iconRepository.icons.get(props.iconName);
    return {
      svg,
    };
  },
};
</script>
