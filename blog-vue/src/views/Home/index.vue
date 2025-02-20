<script setup>
import CardPaginator from '@/components/CardPaginator.vue';
import ArticleCard from './components/ArticleCard.vue';
import TopArticleCard from './components/TopArticleCard.vue';
</script>

<template>
  <div class="article-cards">
    <TopArticleCard></TopArticleCard>
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
.article-cards {
  width: 100%;
}
</style>
