<script setup>
import TimeAndOperation from "@/components/TimeAndOperation.vue";
import { changeArticleShowStatusAPI, deleteArticleAPI } from "@/api/adminArticle";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";

const router = useRouter();

const props = defineProps({
  data: Object
})


const editArticle = () => {
  router.push({ name: 'ToEditArticle', params: { 'editArticleId': props.data.id } })
}
const emit = defineEmits(['delete-success']);
const deleteArticle = async () => {
  const data = [];
  data.push(props.data.id);
  await deleteArticleAPI(data);
  ElMessage.success('删除成功');
  emit('delete-success', props.data.id);
}
const setArticle = () => {
  console.log('设置文章');

}
const operations = [
  { name: '编辑', onClick: editArticle },
  { name: '设置', onClick: setArticle },
  { name: '删除', onClick: deleteArticle },
];
const changeVisible = async () => {
  await changeArticleShowStatusAPI(props.data.id);
  props.data.isShow = !props.data.isShow;
  ElMessage.success('修改成功');
}
</script>

<template>
  <div class="edit-article-card">
    <div class="edit-left">
      <span class="title">{{ props.data.title }}</span>
      <span v-for="(category, index) in props.data.categories" :key="index">
        {{ category }}
      </span>
      <div class="bottom">
        <span> {{ props.data.viewNumber }}</span>
        <span>{{ props.data.commentNumber }}</span>
      </div>
    </div>
    <div class="edit-right">
      <img v-if="props.data.isShow" @click="changeVisible" style="width: 24px; height: 24px;"
        src="@/assets/icons/visible.svg" alt="">
      <img v-else @click="changeVisible" style="width: 24px; height: 24px;" src="@/assets/icons/invisible.svg" alt="">
      <TimeAndOperation :create-time="props.data.createTime" :operations="operations" />
    </div>
  </div>
</template>

<style scoped lang="scss">
.edit-article-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  padding: 2px 0 0 0;
  font-size: 15px;

  .edit-left {
    display: flex;
    flex-direction: column;
    align-items: center;

    .title {
      font-size: 16px;
      padding: 3px 0;
    }

    .bottom {
      display: flex;
      gap: 10px;
      margin-top: 2px;
    }
  }

  .edit-right {
    display: flex;
    align-items: center;
    gap: 30px;

    .el-dropdown-link {
      cursor: pointer;
      font-size: 20px;
      font-weight: 800;
    }

    img {
      cursor: pointer;
    }
  }
}
</style>
