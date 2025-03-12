<script setup>
import HeadOperation from '../../Layout/components/HeadOperation.vue';
import EditOperation from './components/EditOperation.vue';
import RichTextEditor from './components/RichTextEditor.vue';
import ArticleSetting from './components/ArticleSetting.vue';
import { ref } from 'vue';

const showParticipants = ref(false);
const toPublish = () => {
  showParticipants.value = true;

}
</script>

<template>
  <HeadOperation :title="'文章'" :icon="'article.svg'">
    <EditOperation @to-publish="toPublish"></EditOperation>
  </HeadOperation>
  <div class="admin-container">
    <RichTextEditor></RichTextEditor>
    <el-dialog title="文章设置" v-model="showParticipants" width="550px" style="overflow: auto;">
      <ArticleSetting></ArticleSetting>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss"></style>
