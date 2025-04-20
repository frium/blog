<script setup>
import { deleteLinkAPI } from '@/api/links';
import AddLink from './AddLink.vue';
import TimeAndOperation from '@/components/TimeAndOperation.vue';
import { ElMessage } from 'element-plus';
import { ref } from 'vue';

const props = defineProps({
  data: Object
});
const showAddLink = ref(false);
const handelShowLink = () => {
  showAddLink.value = false;
}
const updateLink = () => {
  showAddLink.value = true;
}

const emit = defineEmits(['delete-success']);
const deleteLink = () => {
  const arr = [];
  arr.push(props.data.id);
  deleteLinkAPI(arr);
  ElMessage.success('删除成功!');
  emit('delete-success', props.data.id);
}
const operations = [
  { name: '编辑', onClick: updateLink },
  { name: '删除', onClick: deleteLink }
]

</script>

<template>
  <div class="link-card">
    <div class="left" style="width: 30%;">
      <img :src="props.data.logo" alt="">
      <div class="detail">
        <h4 class="title">{{ props.data.urlName }}</h4>
        <a class="link">{{ props.data.url }}</a>
      </div>
    </div>
    <p style="margin-right: auto;">{{ props.data.description }}</p>

    <TimeAndOperation :create-time="props.data.createTime" :operations="operations" />
  </div>
  <el-dialog title="编辑友链" :append-to-body="true" v-model="showAddLink" width="550px" style="overflow: auto;"
    @close="handelShowLink">
    <AddLink :handelShowLink="handelShowLink" :data="data">
    </AddLink>
  </el-dialog>
</template>

<style scoped lang="scss">
a:hover {
  color: rgb(0, 0, 0) !important;
}

.link-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 55px;

  .left {
    display: flex;
    gap: 20px;
    align-items: center;

    img {
      width: 40px;
      height: 40px;
      object-fit: cover;
      border-radius: 4px;
    }

    .detail {
      display: flex;
      flex-direction: column;
    }
  }

  .right {
    display: flex;
    align-items: center;

    .el-dropdown-link {
      cursor: pointer;
      font-size: 20px;
      font-weight: 800;
      margin-left: 20px;
    }
  }
}
</style>
