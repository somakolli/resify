<template>
  <div>
    <LabelInput class="mt-7" v-for="(value, name) in personalInformationSchema.properties"
                :model-value="reservation.personalInformation[name]"
                @update:modelValue="reservation.personalInformation[name] = $event" :placeholder="prettifyName(name)"
                :key="name"
                :error-text="errorTexts[name]">
      {{ prettifyName(name) }}
    </LabelInput>
  </div>
</template>
<script lang="ts">
import LabelInput from '@/components/shared-components/LabelInput.vue'
import {reservation, nextClick} from '@/pages/_company/_calendar/index.vue'
import {validate} from "jsonschema";
import {setValidAnyMoveOn} from "~/pages/_company/_calendar/index.vue";
import {ref, watchEffect} from "@vue/composition-api";
import {watch} from "@nuxtjs/composition-api";

export const errorTexts = ref<any>({})
export default {
  components: {LabelInput},
  props: {
    calendar: {
      required: true
    },
    startClick: {
      type: Number,
      required: true
    }
  },
  emits: ['valid'],
  setup(props: any, {emit}: any) {
    const personalInformationSchema = JSON.parse(props.calendar.personalInformationSchema);

    for (const name of Object.keys(personalInformationSchema.properties)) {
      errorTexts.value[name] = "";
    }

    function prettifyName(name: string) {
      return name.split('-').map(value => value.charAt(0).toUpperCase() + value.slice(1, value.length)).join(' ');
    }

    function validatePersonalInformation(): boolean {
      let tempError: any = {}
      const validatorResult =
        validate(reservation.value.personalInformation,
          personalInformationSchema);
      validatorResult.errors.forEach((error) => {
        tempError[error.argument] = "field required";
      })
      // have to update the errors in an external function because the
      // composition api doesnt recognize the update in this context.
      // probably to prevent infinite loops but
      // infinite loops can still be created.
      setErrors(tempError);
      return validatorResult.valid;
    }

    function setErrors(errors: any) {
      errorTexts.value = errors;
    }


    setValidAnyMoveOn(props, emit, nextClick, validatePersonalInformation)
    return {
      reservation,
      personalInformationSchema,
      prettifyName,
      errorTexts
    }
  }
}
</script>
