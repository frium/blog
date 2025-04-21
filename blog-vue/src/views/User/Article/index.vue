<script setup>
import MarkdownViewer from './components/MarkdownViewer.vue'
import TopArticleCard from '@/views/User/Home/components/TopArticleCard.vue';
import { onMounted, reactive, ref } from 'vue'
import CommentArea from './components/CommentArea.vue';
import { getArticleAPI } from '@/api/article';
import { useRoute } from 'vue-router';

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
  "commentNum": null
});

const handleComponentLoaded = () => {
  emit('component-loaded');
}

const emit = defineEmits(['component-loaded']);
onMounted(async () => {
  const res = await getArticleAPI(route.params.articleId);
  Object.assign(article, res.data);
})
</script>

<template>
  <div class="article">
    <TopArticleCard :isTop="false" :data="article"></TopArticleCard>
    <MarkdownViewer @component-loaded="handleComponentLoaded" class="markdown" :source="article.content"
      :line-numbers="true" />
    <CommentArea></CommentArea>
    <div class="last-next">
      <RouterLink to="/article/1">{{ '< 上一篇:文章名字' }}</RouterLink>
          <RouterLink to="/article/1">{{ '下一篇:文章名字 >' }}</RouterLink>
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
    background: rgba(40, 44, 52, 0.6);
    border-radius: 8px;
    color: #999;
    margin-bottom: 20px;

  }
}
</style>
