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
  document.body.style.color = 'black';
}
</script>

<template>
  <div class="admin">
    <AdminNav v-if="!route.fullPath.includes('login')" class="admin-nav"></AdminNav>
    <div class="router-view-div">
      <router-view></router-view>
    </div>
  </div>
</template>

<style scoped lang="scss">
.admin {
  position: relative;
  display: flex;

  .admin-nav {
    position: sticky;
    top: 0;
    flex-shrink: 0;

  }

  .router-view-div {
    width: calc(100% - 250px);
  }

  ::selection {
    background: #999798;
    color: #fff;
  }
}
</style>
