<script setup>
import { deleteUsersAPI } from '@/api/adminUser';
import TimeAndOperation from '@/components/TimeAndOperation.vue';
import { ElMessage } from 'element-plus';
import { ref } from 'vue';
import CreatUser from './CreatUser.vue';
const props = defineProps({
  data: Object
})

const updateInfo = () => {
  showDialog.value = true;
}
const emit = defineEmits(['delete-success']);

const showDialog = ref(false);

const handelShowDialog = () => {
  showDialog.value = false;
}
const deleteUser = async () => {
  const arr = [];
  arr.push(props.data.id);
  await deleteUsersAPI(arr);
  ElMessage.success("删除成功");
  emit('delete-success', props.data.id);
}

const operations = [
  { name: '编辑', onClick: updateInfo },
  { name: '删除', onClick: deleteUser },
]
</script>

<template>
  <div class="user-info-card">
    <div class="info-left">
      <img :src="props.data.avatar" alt="">
      <h4>{{ props.data.username }}</h4>
    </div>
    <div class="info-right">
      <div class="identity">
        <span v-if="props.data.auth === 1">超级管理员</span>
        <span v-else-if="props.data.auth === 2">管理员</span>
        <span v-else>访客</span>
      </div>
      <TimeAndOperation :create-time="props.data.createTime" :operations="operations" />
    </div>
    <el-dialog title="编辑用户" :append-to-body="true" v-model="showDialog" width="550px" style="overflow: auto;"
      @close="handelShowDialog">
      <CreatUser :data="props.data" :handel-show-dialog="handelShowDialog"></CreatUser>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss">
.user-info-card {
  display: flex;
  justify-content: space-between;
  align-items: center;

  .info-left {
    display: flex;
    align-items: center;
    gap: 20px;

    img {
      width: 40px;
      height: 40px;
      border-radius: 4px;
    }
  }

  .info-right {
    display: flex;
    align-items: center;
    gap: 20px;

    .identity {
      color: black;
      padding: 1px 4px;
      border: 1px solid rgb(184, 184, 184);
      border-radius: 4px;
    }
  }
}
</style>
