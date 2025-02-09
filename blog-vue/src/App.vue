<script setup>
import LayoutNav from "./components/LayoutNav.vue";
import LayoutFooter from "./components/LayoutFooter.vue";
import PersonalCard from "./components/PersonalCard.vue";
</script>

<template>
  <div class="app">
    <LayoutNav></LayoutNav>
    <div class="out-container">
      <div>
        <PersonalCard class="left"></PersonalCard>
      </div>
      <div class="router-view">
        <router-view></router-view>
      </div>
    </div>
    <LayoutFooter></LayoutFooter>
  </div>
</template>

<style scoped lang="scss">
.app {

  .out-container {
    display: flex;
    gap: 0 60px;

    padding: 0 90px;

    .left {
      position: sticky;
      top: 76px;
    }

    .router-view {
      min-height: 100vh;
      width: 100%;
      min-width: 800px;
      margin-top: 24px;

    }
  }
}
</style>
