<template>
  <div class="text-gray-800" v-html="getSvg"></div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { iconRepository } from './IconRepository';
export default defineComponent({
  name: 'Icon',
  props: {
    iconName: {
      type: String,
      required: true,
    },
  },
  computed: {
    getSvg: function (): any {
      return iconRepository.icons.get(this.iconName);
    },
  },
});
</script>
