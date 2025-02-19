<script setup>
import CardPaginator from '@/components/CardPaginator.vue';
import ArticleCard from './components/ArticleCard.vue';
</script>

<template>
  <div class="article-card">
    <ArticleCard></ArticleCard>
    <ArticleCard></ArticleCard>
    <ArticleCard></ArticleCard>
    <ArticleCard></ArticleCard>
    <ArticleCard></ArticleCard>
    <ArticleCard></ArticleCard>
    <CardPaginator></CardPaginator>
  </div>
</template>

<style scoped lang="scss">
.article-card {
  width: 100%;
}
</style>
