<script setup>
import TimeAndOperation from '@/components/TimeAndOperation.vue';
const props = defineProps({
  data: Object
});
const setLink = () => {
  console.log('编辑友链');
}


const deleteLink = () => {
  console.log('删除友链');
}
const operations = [
  { name: '编辑', operate: setLink },
  { name: '删除', operate: deleteLink }
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
