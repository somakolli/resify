<template>
  <div class="flex flex-col h-full w-11/12 mx-auto overflow-auto">
    <header class="flex flex-row-reverse items-center mt-5">
      <Icon @click="createServiceActive = true" icon-name="plus" class="w-8 h-8 cursor-pointer"></Icon>
      <Icon
        @click="edit = !edit"
        icon-name="pencil"
        class="w-6 h-6 cursor-pointer mr-2"
      ></Icon>
    </header>
    <div class="flex flex-col w-full h-full overflow-auto px-1 pb-1">
      <ListItem
        v-for="service in services"
        :key="service.name"
        icon-name="trash"
        :main-content="service.name"
        :side-content="service.duration + 'm'"
        :show-icon="edit"
        class="mt-6"
      >
      </ListItem>
    </div>
    <PopUpModal
      v-if="createServiceActive"
      confirm-button-text="Create"
      title="Create new Service"
      @close="createServiceActive = false"
      @confirm="createServiceRequest"
    >
      <LabelInput
        type="text"
        placeholder="Service name"
        v-model="newService.name"
      >
        Chose a service name
      </LabelInput>
      <LabelInput
        class="mt-3"
        type="number"
        placeholder="Duration"
        v-model="newService.duration"
      >
        Set the duration
      </LabelInput>
    </PopUpModal>
  </div>
</template>
<script lang="ts">
import Icon from '@/components/Icon.vue';
import { ref } from 'vue';
import { Service } from '@/models/Service';
import ListItem from '@/components/ListItem.vue';
import { ServiceService } from '@/services/ServiceService';
import { CalendarService } from '@/services/CalendarService';
import PopUpModal from '@/components/PopUpModal.vue';
import LabelInput from '@/components/LabelInput.vue';
export default {
  components: { LabelInput, PopUpModal, ListItem, Icon },
  props: {
    route: {
      type: String,
      required: true
    }
  },
  setup(props) {
    const edit = ref(false);
    const services = ref(new Array<Service>());
    const serviceService = new ServiceService(props.route);
    const calendarService = new CalendarService();
    const createServiceActive = ref(false);
    const newService = ref<Service>(new Service('', 30));
    async function createServiceRequest() {
      const service = await serviceService.createService(newService.value);
      services.value.push(service);
      createServiceActive.value = false;
    }
    calendarService.getCalendar(props.route).then(calendar => {
      services.value = calendar.services;
    });
    return {
      edit,
      services,
      createServiceActive,
      createServiceRequest,
      newService
    };
  }
};
</script>
