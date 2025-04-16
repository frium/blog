<script setup>
import { deleteCommentAPI, passCommentAPI } from '@/api/comment';
import TimeAndOperation from '@/components/TimeAndOperation.vue';
import { ElMessage } from 'element-plus';

const props = defineProps({
  data: Object
})
const passComment = async () => {
  await passCommentAPI(props.data.id);
  ElMessage.success('审核通过!');
  emit('pass-success', props.data.id);
}
const deleteComment = async () => {
  const arr = [props.data.id];
  await deleteCommentAPI(arr);
  ElMessage.success('删除成功!')
  emit('delete-success', props.data.id);
}
const emit = defineEmits(['pass-success', 'delete-success'])

const operations = [
  { name: '审核通过', onClick: passComment },
  { name: '删除', onClick: deleteComment }
]

const deleteOperations = [
  { name: '删除', onClick: deleteComment }
]

</script>

<template>
  <div class="comment-card">
    <div class="left">
      <img :src="props.data.avatar" alt="">
      <div style="width: 20%;">
        <h4 class="username">{{ props.data.username }}</h4>
        <p class="text">{{ props.data.email }}</p>
      </div>
      <div style="width: 70%; margin-left: 20px;">
        <h4>{{ props.data.articleTitle }}</h4>
        <p class="text">{{ props.data.commentContent }}</p>
      </div>
    </div>
    <TimeAndOperation v-if="props.data.status" :create-time="props.data.createTime" :operations="deleteOperations" />
    <TimeAndOperation v-else :create-time="props.data.createTime" :operations="operations" />
  </div>
</template>

<style scoped lang="scss">
.comment-card {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 55px;

  .left {
    display: flex;
    align-items: center;
    gap: 20px;
    width: 70%;

    .text {
      font-size: 15px;
      white-space: nowrap;
      word-break: break-all;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 1;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    img {
      width: 40px;
      height: 40px;
      border-radius: 4px;
    }
  }

  .right {
    display: flex;
    align-items: center;
    gap: 20px;

    .el-dropdown-link {
      cursor: pointer;
      font-size: 20px;
      font-weight: 800;
    }
  }
}
</style>
