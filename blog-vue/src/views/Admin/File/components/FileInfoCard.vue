<script setup>
import TimeAndOperation from '@/components/TimeAndOperation.vue';
const props = defineProps({
  data: Object
})
const deleteFile = () => {
  console.log('删除附件');

}
const operations = [
  { name: '删除', onClick: deleteFile }
];
</script>

<template>
  <div class="file-info-card">
    <div class="left">
      <img :src="props.data.url" alt="">
      <div class="detail">
        <p class="text">{{ props.data.fileName }}</p>
        <span>{{ props.data.fileType }}</span>
        <span>{{ props.data.fileSzie }}</span>
      </div>
    </div>
    <TimeAndOperation :create-time="props.data.createTime" :operations="operations" />
  </div>
</template>

<style scoped lang="scss">
.file-info-card {
  display: flex;
  justify-content: space-between;

  .left {
    display: flex;
    align-items: center;
    gap: 20px;
    width: 70%;

    .detail {
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