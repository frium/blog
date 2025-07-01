<script setup>
import HeadOperation from '../../Layout/components/HeadOperation.vue';
import RichTextEditor from './components/RichTextEditor.vue';
import ArticleSetting from './components/ArticleSetting.vue';
import { onMounted, ref } from 'vue';
import { getArticleAPI } from '@/api/article';
import { useRoute } from 'vue-router';
import { useEditArticleStore } from '@/stores/editArticle';
import { updateArticleAPI } from '@/api/adminArticle';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';
const route = useRoute();
const router = useRouter();
const showArticleSetting = ref(false);
const editArticleStore = useEditArticleStore();

const toPublish = async () => {
  if (route.params.editArticleId) {//修改文章不需要配置弹窗
    if (!typeof editArticleStore.article.label[0] === 'number') editArticleStore.article.label = editArticleStore.article.label.map(item => item.id);
    await updateArticleAPI(editArticleStore.article);
    ElMessage.success('修改成功!');
    return;
  }
  showArticleSetting.value = true;
}

const toShowArticleSetting = () => {
  showArticleSetting.value = true;
}

const closeArticleSetting = () => {
  showArticleSetting.value = false;
}

const getCategories = () => {
  router.push({ name: 'LabelList' });
}
const buttonArr = [
  { name: "分类", onClick: getCategories },
  { name: "设置", onClick: toShowArticleSetting },
  { name: "发布", onClick: toPublish },
];
onMounted(async () => {
  if (!route.params.editArticleId) return;
  const res = await getArticleAPI(route.params.editArticleId);
  if (res.code === 4010) {
    router.push({ name: 'ToEditArticle' })
    return;
  }
  editArticleStore.article = res.data;
})

</script>

<template>
  <HeadOperation :title="'文章'" :icon="'article.svg'" :button-arr="buttonArr"></HeadOperation>
  <div class="admin-container">
    <RichTextEditor></RichTextEditor>
    <el-dialog title="文章设置" v-model="showArticleSetting" width="550px" style="overflow: auto;">
      <ArticleSetting :close-article-setting="closeArticleSetting"></ArticleSetting>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss"></style>
