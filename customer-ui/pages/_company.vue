<template>
  <div>
    <div v-for="calendar in calendars">
      {{calendar.calendarName}}
    </div>
  </div>
</template>
<script lang="ts">
export default {
  async asyncData({ params, $http }: any) {
    const calendars = await $http.$get(`public/${params.company}`)
    return {
      calendars
    }
  },
}
</script>
