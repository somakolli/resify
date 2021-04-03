<template>
  <div class="flex flex-col">
    <label class="font-bold"><slot></slot></label>
    <div class="w-11/12 self-center mt-3">
      <div class="w-full relative inline-flex items-center self-center">
        <input
          v-model="inputText"
          class="py-2 px-3 rounded self-center shadow w-full"
          :placeholder="placeholder"
          :type="type"
        />
        <Icon
          v-if="errorText"
          icon-name="exclamation-circle"
          class="w-6 h-6 absolute right-2 text-red-500"
        ></Icon>
      </div>
      <div v-if="errorText" class="text-red-500 text-sm ml-2.5">
        {{ errorText }}
      </div>
    </div>
  </div>
</template>
<script>
import { useModelWrapper } from '@/ModelWrapper';
import Icon from './Icon.vue';
export default {
  components: { Icon },
  props: {
    modelValue: {
      type: String,
      required: false,
      default: '',
    },
    placeholder: {
      type: String,
      required: true,
    },
    type: {
      type: String,
      required: false,
      default: 'text',
    },
    errorText: {
      type: String,
      required: false,
      default: '',
    },
  },
  emits: ['update:modelValue'],
  setup(props, { emit }) {
    return {
      inputText: useModelWrapper(props, emit),
    };
  },
};
</script>
