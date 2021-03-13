<template>
  <div
    class="w-screen h-screen fixed z-10 bg-black bg-opacity-20 flex flex-col items-center justify-center px-5 left-0 top-0"
    style="backdrop-filter: blur(1px)"
  >
    <div
      v-click-outside="close"
      class="w-full max-w-96 sm:w-96 min-h-24 bg-white shadow-2xl rounded-xl"
    >
      <div class="flex h-full flex-col justify-between py-4">
        <span class="self-center font-bold">{{ title }}</span>
        <div class="w-10/12 self-center py-5">
          <slot></slot>
        </div>
        <div class="flex flex-row-reverse">
          <Button
            @click="$emit('confirm')"
            primary
            class="mx-4 w-24 h-8 font-bold"
            >Create</Button
          >
          <Button class="font-bold w-24 h-8" @click="close">Cancel</Button>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import { ref } from 'vue';
import Button from '@/components/Button.vue';

export default {
  components: { Button },
  emits: ['close', 'confirm'],
  props: {
    title: {
      type: String,
      required: true
    },
    confirmButtonText: {
      type: String,
      required: true
    }
  },
  setup(_, context) {
    const firstClose = ref(true);
    function close() {
      if (firstClose.value) {
        firstClose.value = false;
      } else {
        context.emit('close');
      }
    }
    return {
      close
    };
  }
};
</script>
