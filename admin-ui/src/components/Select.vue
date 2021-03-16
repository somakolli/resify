<template>
  <div class="relative self-center ml-3" v-click-outside="collapse">
    <div
      class="cursor-pointer flex w-full justify-between rounded px-3 py-1"
      :class="shadow"
      @click="changeVisibility"
    >
      <div>{{ options[selectedItem] }}</div>
      <Icon icon-name="down" class="ml-3 w-4 h-4 self-center"></Icon>
    </div>
    <div
      class="absolute flex-col w-full bg-gray-white rounded mt-2 px-3 py-1 shadow"
      :class="active"
    >
      <span
        class="cursor-pointer"
        v-for="(option, index) in options"
        @click="
          $emit('update:selectedItem', index);
          selectedItemIndex = index;
          changeVisibility();
        "
        :key="index"
      >
        {{ option }}
      </span>
    </div>
  </div>
</template>
<script>
import Icon from './Icon';
export default {
  components: { Icon },
  mixins: [],
  emits: ['update:selectedItem'],
  props: {
    options: {
      type: Array,
      required: true,
    },
    selectedItem: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      active: 'hidden',
      shadow: '',
    };
  },
  methods: {
    changeVisibility() {
      if (this.active === 'flex') {
        this.active = 'hidden';
        this.shadow = '';
      } else {
        this.active = 'flex';
        this.shadow = 'shadow';
      }
    },
    collapse() {
      this.active = 'hidden';
      this.shadow = '';
    },
  },
};
</script>
