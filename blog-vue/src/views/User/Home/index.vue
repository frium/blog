<script setup>
import CardPaginator from '@/components/CardPaginator.vue';
import ArticleCard from './components/ArticleCard.vue';
import TopArticleCard from './components/TopArticleCard.vue';
import { computed, onMounted, ref, watch } from 'vue';
import { useRoute } from 'vue-router';
import { getShowArticleListAPI, getShowArticleNumAPI } from '@/api/article';

const route = useRoute();

const pageId = computed(() => {
  return parseInt(route.params.pageId) || 1;
});

watch(pageId, async (newId) => {
  const res = await getShowArticleListAPI(newId);
  dataArr.value = res.data;
});

const articleNum = ref(0);
const dataArr = ref([]);

onMounted(async () => {
  const res = await getShowArticleListAPI(pageId.value);
  dataArr.value = res.data;
  const numRes = await getShowArticleNumAPI();
  articleNum.value = numRes.data;
})
</script>

<template>
  <div class="article-cards">
    <template v-for="data in dataArr" :key="data.id">
      <TopArticleCard v-if="data.isTop" :data="data"></TopArticleCard>
      <ArticleCard v-else :data="data"></ArticleCard>
    </template>
    <CardPaginator v-if="articleNum > 7" :number-of-page="Math.ceil(articleNum / 7)" :show-num="7"></CardPaginator>
  </div>
</template>

<style scoped lang="scss">
.article-cards {
  width: 100%;
}
</style>
