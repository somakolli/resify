<template>
  <div @click="$emit('click')" class="text-gray-800" v-html="svg"></div>
</template>

<script lang="ts">
import { iconRepository } from "./IconRepository";
import { ref } from "composition-api-source";
export default {
  name: "Icon",
  emits: ["click"],
  props: {
    iconName: {
      type: String,
      required: true,
    },
  },
  setup(props: any) {
    const svg = ref("");
    svg.value = iconRepository.icons.get(props.iconName)!.toString();
    return {
      svg,
    };
  },
};
</script>
