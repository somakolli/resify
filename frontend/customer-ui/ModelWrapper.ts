import { computed } from '@vue/composition-api';

export function useModelWrapper(props: any, emit: any) {
  return computed({
    get: () => props.modelValue,
    set: (value) => emit('update:modelValue', value),
  });
}
