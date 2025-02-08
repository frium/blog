<script setup>
import LayoutNav from "./components/LayoutNav.vue";
import LayoutFooter from "./components/LayoutFooter.vue";
import PersonalCard from "./components/PersonalCard.vue";
</script>

<template>
  <div class="app">
    <LayoutNav></LayoutNav>
    <div class="right">
      <PersonalCard></PersonalCard>
    </div>
    <div class="router-view">
      <router-view></router-view>

    </div>
    <LayoutFooter></LayoutFooter>
  </div>
</template>

<style scoped lang="scss">
.app {
  position: relative;

  .right {
    position: absolute;
    top: 100px;
    right: 45px;
  }

  .router-view {
    min-height: 100vh;
  }
}
</style>
