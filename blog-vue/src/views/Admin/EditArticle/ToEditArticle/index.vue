<script setup>
import HeadOperation from '../../Layout/components/HeadOperation.vue';
import RichTextEditor from './components/RichTextEditor.vue';
import ArticleSetting from './components/ArticleSetting.vue';
import { ref } from 'vue';

const showArticleSetting = ref(false);
const toPublish = () => {
  showParticipants.value = true;
}

const toShowArticleSetting = () => {
  showArticleSetting.value = true
}

const getCategories = () => {
  console.log('getCategories')
}
const buttonArr = [
  { name: "分类", onClick: getCategories },
  { name: "设置", onClick: toShowArticleSetting },
  { name: "发布", onClick: toPublish },
];

</script>

<template>
  <HeadOperation :title="'文章'" :icon="'article.svg'" :button-arr="buttonArr">
    <EditOperation @to-publish="toPublish"></EditOperation>
  </HeadOperation>
  <div class="admin-container">
    <RichTextEditor></RichTextEditor>
    <el-dialog title="文章设置" v-model="showArticleSetting" width="550px" style="overflow: auto;">
      <ArticleSetting></ArticleSetting>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss"></style>
