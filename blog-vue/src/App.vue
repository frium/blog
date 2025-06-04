<script setup>
import { onMounted } from "vue";
import { getBlogInfoAPI } from "./api/blog";
import { useGlobalInfoStore } from "./stores/globalInfo";

const globalInfoStore = useGlobalInfoStore();

onMounted(async () => {
  const res = await getBlogInfoAPI();
  globalInfoStore.globalInfo = res.data;
})
</script>

<template>
  <router-view></router-view>
</template>

<style scoped lang="scss"></style>
