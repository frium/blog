<script setup>
import { ref } from "vue"
import TimeAndOperation from "@/components/TimeAndOperation.vue";
const props = defineProps({
  data: Object
})


const visible = ref(true);
const toUser = () => {

}

const editArticle = () => {
  console.log('编辑文章');

}

const deleteArticle = () => {

}
const setArticle = () => {
  console.log('设置文章');

}
const operations = [
  { name: '编辑', onClick: editArticle },
  { name: '设置', onClick: setArticle },
  { name: '删除', onClick: deleteArticle },
];
const changeVisible = () => {
  visible.value = !visible.value;
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
      <img @click="toUser" style="width: 35px; height: 35px; border-radius: 50%; "
        src="https://blog.frium.top/upload/7b914c84-b6ec-4406-8d8b-976eee71e502.png" alt="">
      <img v-if="visible" @click="changeVisible" style="width: 24px; height: 24px;" src="@/assets/icons/visible.svg"
        alt="">
      <img v-else @click="changeVisible" style="width: 24px; height: 24px;" src="@/assets/icons/invisible.svg" alt="">
      <span> 2024-10-15 21:49 </span>
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
  padding: 2px 0;
  font-size: 15px;

  .edit-left {
    display: flex;
    flex-direction: column;

    .title {
      margin-bottom: 2px;
      font-size: 16px;
      color: black;
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
