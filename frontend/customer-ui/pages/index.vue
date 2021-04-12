<template>
  <div
    class="flex items-center justify-center flex-col max-w-xl mx-auto"
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
      :class="{ 'mt-5 w-8/12': !focus, 'mt-0 w-10/12': focus }"
      class="shadow h-12 rounded-lg text-lg px-6 list-item"
      placeholder="Search"
      type="text"
      key="search"
      v-model="searchText"
    />
    <div class="flex flex-col max-w-lg w-10/12">
      <NuxtLink :to="`/${company.name}`" v-for="company in companies" :key="company.name" class="w-full mt-5">
        <div class="flex flex-row justify-between w-full items-center">
          <span class="font-bold">{{company.name}}</span>
          <div class="flex flex-row justify-center items-center shadow h-8 w-24 rounded-full">
            <div class="w-full m-auto flex items-center justify-center">
              <Icon icon-name="calendar" class="w-6 h-6 "></Icon>
            </div>
              <div class="m-auto shadow rounded-full w-full text-center h-full flex items-center justify-center">{{company.calendarCount}}</div>
          </div>
        </div>
        <div class="mt-3">
          <p v-if="company.description">
            {{company.description}}
          </p>
          <p v-else>
            Company Description!
          </p>
        </div>
      </NuxtLink>
    </div>
  </div>
</template>

<script lang="ts">
import { ref } from "@vue/composition-api";
import Icon from "../components/shared-components/Icon.vue";
import {watch} from "@nuxtjs/composition-api";
export default {
  components: { Icon },
  setup() {
    const focus = ref(false);
    function searchFocus() {
      focus.value = true;
    }
    const searchText = ref('')
    const companies = ref<any>();
    watch((searchText), () => {
      if(searchText)
        searchFocus()
      fetch(process.env.baseUrl + '/public/search-company?q=' + searchText.value)
        .then(
        (result) => result.json().then(resultJson => companies.value = resultJson)
      )
    })
    return { searchFocus, focus, searchText, companies };
  },
  async fetch() {},
};
</script>
<style scoped>
</style>
