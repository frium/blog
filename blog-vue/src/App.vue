<script setup>
import LayoutNav from "./components/LayoutNav.vue";
import LayoutFooter from "./components/LayoutFooter.vue";

</script>

<template>
  <LayoutNav></LayoutNav>
  <router-view></router-view>
  <LayoutFooter></LayoutFooter>
</template>

<style scoped lang="scss"></style>
