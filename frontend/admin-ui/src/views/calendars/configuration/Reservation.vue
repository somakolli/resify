<template>
  <div class="flex flex-col overflow-auto items-center">
    <div
      v-for="copyItem in copyItems"
      :key="copyItem.title"
      class="flex-col w-10/12 pb-1 mt-10"
    >
      <div class="flex">
        <div>
          {{ copyItem.title }}
        </div>
        <Icon icon-name="information-circle" class="ml-1 w-6 h-6"></Icon>
      </div>
      <ListItem
        icon-name="clipboard"
        show-icon
        :main-content="copyItem.link"
        class="mt-6"
      ></ListItem>
    </div>
  </div>
</template>
<script>
import Icon from '@components/Icon.vue';
import ListItem from '../../../components/ListItem.vue';
import { ref } from 'vue';
export default {
  components: { ListItem, Icon },
  setup() {
    const copyItems = ref([
      {
        title: 'Customer Link',
        link: 'customer.freshspark.com/jonas-calendar',
      },
      {
        title: 'Widget',
        link:
          '<iframe src="customer.freshspark.com/jonas-calender" title="W3Schools Free Online Web Tutorials"></iframe>',
      },
    ]);
    return {
      copyItems,
    };
  },
};
</script>
