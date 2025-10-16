<script setup>
import CardPaginator from '@/components/CardPaginator.vue';
import ArticleCard from './components/ArticleCard.vue';
import TopArticleCard from './components/TopArticleCard.vue';
import { computed, onMounted, ref, watch } from 'vue';
import { useRoute } from 'vue-router';
import { searchArticleAPI, getSearchArticleNumAPI } from '@/api/article';
import { useArticleStore } from '@/stores/article';

const artilceStore = useArticleStore()
const route = useRoute();
const pageId = computed(() => {
  return parseInt(route.params.pageId) || 1;
});

watch(pageId, async (newId) => {
  artilceStore.searchData.pageNum = newId;
  const res = await searchArticleAPI(artilceStore.searchData);
  artilceStore.articleArr = res.data;
});

onMounted(async () => {
  artilceStore.searchData.searchInfo = ''
  const [numRes, res] = await Promise.all([getSearchArticleNumAPI(artilceStore.searchData),
  searchArticleAPI(artilceStore.searchData)]);
  artilceStore.articleNum = numRes.data;
  artilceStore.articleArr = res.data;
})
</script>

<template>
  <div class="article-cards">
    <template v-for="data in artilceStore.articleArr" :key="data.id">
      <TopArticleCard v-if="data.isTop" :data="data"></TopArticleCard>
      <ArticleCard v-else :data="data"></ArticleCard>
    </template>
    <CardPaginator style="margin: 10px 0 20px 0" v-if="artilceStore.articleNum > 7"
      :number-of-page="Math.ceil(artilceStore.articleNum / 7)" :show-num="7">
    </CardPaginator>
  </div>
</template>

<style scoped lang="scss">
.article-cards {
  width: 100%;

  .not-thing {
    position: absolute;
    right: 50%;
    top: 50%;
    transform: translate(0, -50%);
  }
}
</style>
