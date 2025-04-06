<script setup>
import CardPaginator from '@/components/CardPaginator.vue';
import ArticleCard from './components/ArticleCard.vue';
import TopArticleCard from './components/TopArticleCard.vue';
import { computed, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { getShowArticleListAPI } from '@/api/article';

const route = useRoute();

const pageId = computed(() => {
  return parseInt(route.params.pageId) || 1;
});

const dataArr = ref([]);

onMounted(async () => {
  const res = await getShowArticleListAPI(pageId.value);
  dataArr.value = res.data;
})
</script>

<template>
  <div class="article-cards">
    <template v-for="data in dataArr" :key="data.id">
      <TopArticleCard v-if="data.isTop" :data="data"></TopArticleCard>
      <ArticleCard v-else :data="data"></ArticleCard>
    </template>
    <CardPaginator></CardPaginator>
  </div>
</template>

<style scoped lang="scss">
.article-cards {
  width: 100%;
}
</style>
