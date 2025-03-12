<script setup>
import HeadOperation from '../Layout/components/HeadOperation.vue';
import EditOperation from './ArticleList/components/EditOperation.vue';
</script>

<template>
  <HeadOperation :title="'文章'" :icon="'article.svg'">
    <EditOperation></EditOperation>
  </HeadOperation>
  <div class="admin-container">
    <router-view></router-view>

  </div>
</template>

<style scoped lang="scss"></style>