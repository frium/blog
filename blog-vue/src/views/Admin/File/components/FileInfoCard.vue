<script setup>
import { deleteFileAPI } from '@/api/file';
import TimeAndOperation from '@/components/TimeAndOperation.vue';
import { ElMessage } from 'element-plus';
const props = defineProps({
  data: Object
});


const emit = defineEmits(['delete-success']);
const deleteFile = async () => {
  const list = [];
  list.push(props.data.id);
  await deleteFileAPI(list);
  ElMessage.success('删除成功!');
  emit('delete-success', props.data.id);
}
const operations = [
  { name: '删除', onClick: deleteFile }
];
</script>

<template>
  <div class="file-info-card">
    <div class="left">
      <img v-lazyLoad="props.data.url" alt="">
      <div class="detail">
        <p class="text">{{ props.data.fileName }}</p>
        <span>{{ 'size: ' + props.data.size + 'KB' }}</span>
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
      width: 60px;
      height: 45px;
      border-radius: 4px;
      object-fit: contain;
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
