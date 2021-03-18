<template>
  <div>{{ calendar.calendarName }}</div>
</template>

<script lang="ts">
export default {
  async asyncData({ params, $http }: any) {
    //TODO: get calendar from previous request
    const calendar = await $http.$get(
      `public/${params.company}/${params.calendar}`
    );
    return {
      calendar,
    };
  },
};
</script>
