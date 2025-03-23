<script setup>
import TimeAndOperation from '@/components/TimeAndOperation.vue';

const props = defineProps({
  data: Object
})

const updateInfo = () => {
  console.log('修改用户信息');

}

const deleteUser = () => {
  console.log('删除用户');
}

const operations = [
  { name: '编辑', onClick: updateInfo },
  { name: '删除', onClick: deleteUser },
]
</script>

<template>
  <div class="user-info-card">
    <div class="info-left">
      <img src="https://blog.frium.top/upload/7b914c84-b6ec-4406-8d8b-976eee71e502.png" alt="">
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
    gap: 10px;

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