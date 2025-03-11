<script setup>
import { useRoute } from 'vue-router';
import AdminNav from './Layout/components/AdminNav.vue';
const route = useRoute();
import { onMounted, watch } from 'vue';

onMounted(() => {
  updateBodyStyle();
});
const updateBodyStyle = () => {
  document.body.style.background = '#f0f4f8';
}
</script>

<template>
  <AdminNav v-if="!route.fullPath.includes('login')"></AdminNav>
  <router-view></router-view>
</template>

<style scoped lang="scss"></style>
