<template>
  <div
    class="flex items-center justify-center flex-col"
    :class="{ 'mt-8': !focus, 'mt-2': focus }"
  >
    <Icon
      v-if="!focus"
      class="list-item w-28 h-28"
      key="logo"
      iconName="logo"
    ></Icon>
    <Icon v-if="!focus" iconName="name" class="w-32 mt-8" key="name"></Icon>
    <input
      @focus="searchFocus"
      :class="{ 'mt-5 w-8/12': !focus, 'mt-0 w-10/12': focus }"
      class="shadow h-12 rounded-lg text-lg px-6 list-item"
      placeholder="Search"
      type="text"
      key="search"
    />
  </div>
</template>

<script lang="ts">
import { ref } from "@vue/composition-api";
import Icon from "../components/shared-components/Icon.vue";
export default {
  components: { Icon },
  setup() {
    const focus = ref(false);
    function searchFocus() {
      focus.value = true;
    }
    return { searchFocus, focus };
  },
  async fetch() {},
};
</script>
<style scoped>
.list-move {
  transition: transform 2s ease;
}
</style>
