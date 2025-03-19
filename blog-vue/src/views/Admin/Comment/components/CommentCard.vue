<script setup>
import TimeAndOperation from '@/components/TimeAndOperation.vue';

const props = defineProps({
  data: Object
})
const passComment = () => {
  console.log('审核通过');
}
const deleteComment = () => {
  console.log('审核通过');
}

const operations = [
  { name: '审核通过', operate: passComment },
  { name: '删除', operate: deleteComment }
]


</script>

<template>
  <div class="comment-card">
    <div class="left">
      <img src="http://static.frium.top/blog/hutao.jpg" alt="">
      <div style="width: 20%;">
        <h4 class="username">{{ props.data.username }}</h4>
        <p class="text">{{ props.data.email }}</p>
      </div>
      <div style="width: 70%; margin-left: 20px;">
        <h4>{{ props.data.articleTitle }}</h4>
        <p class="text">{{ props.data.comment }}</p>
      </div>
    </div>
    <TimeAndOperation :create-time="props.data.createTime" :operations="operations" />
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
