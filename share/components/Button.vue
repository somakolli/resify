<template>
  <div
    @click="$emit('click')"
    :class="{
      'border border-green-700 text-green-700': primary,
      'shadow-none': text,
    }"
    class="flex justify-center items-center shadow-md rounded py-1 px-2 text-sm cursor-pointer"
  >
    <span><slot></slot></span>
  </div>
</template>
<script lang="ts">
export default {
  emits: ["click"],
  props: {
    primary: {
      type: Boolean,
      required: false,
    },
    text: {
      type: Boolean,
      required: false,
    },
  },
};
</script>
