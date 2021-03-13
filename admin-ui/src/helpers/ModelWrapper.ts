import { computed } from 'vue';

export function useModelWrapper(props: any, emit: any) {
  return computed({
    get: () => props.modelValue,
    set: (value) => emit('update:modelValue', value),
  });
}
