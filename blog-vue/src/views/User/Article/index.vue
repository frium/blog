<script setup>
import MarkdownViewer from './components/MarkdownViewer.vue'
import TopArticleCard from '@/views/User/Home/components/TopArticleCard.vue';
import { onMounted, reactive, ref, watch } from 'vue'
import CommentArea from './components/CommentArea.vue';
import { getArticleAPI } from '@/api/article';
import { useRoute } from 'vue-router';
import { useUserStore } from '@/stores/userStore';
const token = useUserStore().jwt;
const route = useRoute();
const article = reactive({
  "id": null,
  "title": "",
  "label": [],
  "coverImg": "",
  "summary": "",
  "content": "",
  "isShow": null,
  "isTop": null,
  "createTime": "",
  "viewNum": null,
  "commentNum": null,
  'prevArticleName': '',
  'prevArticleId': null,
  'nextArticleName': '',
  'nextArticleId': null
});
const handleComponentLoaded = () => {
  emit('component-loaded');
}

const emit = defineEmits(['component-loaded']);
onMounted(async () => {
  const res = await getArticleAPI(route.params.articleId);
  Object.assign(article, res.data);
})
watch(
  () => route.params.articleId,
  async (newId) => {
    const res = await getArticleAPI(newId);
    Object.assign(article, res.data);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  },
  { immediate: true }
);
</script>

<template>
  <div class="article">
    <TopArticleCard :isTop="false" :data="article"></TopArticleCard>
    <MarkdownViewer @component-loaded="handleComponentLoaded" class="markdown" :source="article.content"
      :line-numbers="true" />
    <CommentArea v-if="token"></CommentArea>
    <div class="last-next">
      <RouterLink :to="'/article/' + article.prevArticleId">{{ '上一篇: ' + article.prevArticleName }}</RouterLink>
      <RouterLink :to="'/article/' + article.nextArticleId">{{ '下一篇: ' + article.nextArticleName }}
      </RouterLink>
    </div>
  </div>
</template>

<style scoped lang="scss">
.article {
  width: 100%;

  .last-next {
    display: flex;
    justify-content: space-around;
    margin-top: 20px;
    width: 100%;
    padding: 14px;
    background: var(--bg-color);
    border-radius: 8px;
    color: #999;
    margin-bottom: 20px;

  }
}
</style>
